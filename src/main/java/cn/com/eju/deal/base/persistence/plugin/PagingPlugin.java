package cn.com.eju.deal.base.persistence.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.builder.CacheRefResolver;
import org.apache.ibatis.builder.ResultMapResolver;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.result.DefaultResultHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.eju.deal.base.persistence.dialect.Dialect;
import cn.com.eju.deal.core.reflect.MetaObject;
import cn.com.eju.deal.core.support.Constant;
import cn.com.eju.deal.core.support.QueryConst;
import cn.com.eju.deal.core.util.SqlUtil;
import cn.com.eju.deal.core.util.StringShiftUti;

/**   
* (Mybatis的分页查询插件，通过拦截Executor的query方法来实现.
* 只有在参数列表中包括Page类型的参数时才进行分页查询。
* 在多参数的情况下，只对第一个Page类型的参数生效。
* 另外，在参数列表中，Page类型的参数无需用@Param来标注)
* @author (li_xiaodong)
* @date 2015年10月21日 上午10:14:57
*/
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
    RowBounds.class, ResultHandler.class})})
public class PagingPlugin extends BasePlugin
{
    /* 日志 */
    private static final Logger log = LoggerFactory.getLogger(PagingPlugin.class);
    
    @Override
    public Object intercept(Invocation invocation)
        throws Throwable
    {
        Object result = null;
        Executor executor = (Executor)unProxy(invocation.getTarget());
        executor = unDelegate(executor);
        
        // 拿参数
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement)args[0];
        
        boolean isExecuted = false;
        if (args[1] instanceof Map<?, ?>)
        {
            //特殊sql文，不进入sql处理--
            Map<String, Object> params = (Map<String, Object>)args[1];
            boolean pageflg = true;
            if (params.containsKey(Constant.SQL_UN_CONTROL))
            {
                if(null!=params.get(Constant.SQL_UN_CONTROL)){
                    pageflg = (boolean)params.get(Constant.SQL_UN_CONTROL);
                }
            }
            
            if (pageflg)
            {
                
                // 获取列名映射
                Map<String, String> columnMap = getColumnMap(mappedStatement);
                
                // 获取参数并判定是否需要分页
                Map<String, String> paramsMap = (Map<String, String>)args[1];
                
                // 规整SQL文
                synchronized (mappedStatement)
                {
                    final BoundSql boundSql = mappedStatement.getBoundSql(args[1]);
                    synchronized (boundSql)
                    {
                        String nativeSql = boundSql.getSql();
                        String formatedSql = formatSql(nativeSql, paramsMap, columnMap);
                        
                        // 判定分页
                        String pagedSql = doPaging(paramsMap, mappedStatement, formatedSql, executor, boundSql);
                        
                        // 参数伪装：SQL
                        MetaObject<BoundSql> boundSqlMeta = MetaObject.fromObject(boundSql);
                        boundSqlMeta.setValue("sql", pagedSql);
                        
                        // 参数伪装：SqlSource
                        MetaObject<MappedStatement> mappedStatementMeta = MetaObject.fromObject(mappedStatement);
                        final SqlSource sqlSource = (SqlSource)mappedStatementMeta.getValue("sqlSource");
                        SqlSource proxyedSqlSource =
                            (SqlSource)Proxy.newProxyInstance(sqlSource.getClass().getClassLoader(),
                                sqlSource.getClass().getInterfaces(),
                                new InvocationHandler()
                                {
                                    @Override
                                    public Object invoke(Object proxy, Method method, Object[] args)
                                        throws Throwable
                                    {
                                        Object result = method.invoke(sqlSource, args);
                                        
                                        if ("getBoundSql".equals(method.getName()))
                                        {
                                            result = boundSql;
                                        }
                                        
                                        return result;
                                    }
                                });
                        mappedStatementMeta.setValue("sqlSource", proxyedSqlSource);
                        
                        // 执行
                        try
                        {
                            result = invocation.proceed();
                            isExecuted = true;
                        }
                        catch (Exception e)
                        {
                            if (log.isErrorEnabled())
                            {
                                log.error("SQL执行错误!!!", e);
                                log.error("SQL:{}", pagedSql);
                            }
                            
                            throw e;
                        }
                        finally
                        {
                            // 参数恢复：SqlSource
                            mappedStatementMeta.setValue("sqlSource", sqlSource);
                            boundSqlMeta.setValue("sql", nativeSql); // 不知道有没有用哈
                        }
                    }
                }
            }
            
        }
        
        if (!isExecuted)
        {
            result = invocation.proceed();
        }
        
        return result;
    }
    
    /** 
    * TODO (这里用一句话描述这个方法的作用)
    * @param paramsMap
    * @param mappedStatement
    * @param formatedSql
    * @param executor
    * @param boundSql
    * @return
    */
    private String doPaging(Map<String, String> paramsMap, MappedStatement mappedStatement, String formatedSql,
        Executor executor, final BoundSql boundSql) throws Exception
    {
        String pagedSql = formatedSql;
        if (paramsMap.containsKey(QueryConst.PAGE_IDX))
        {
            Dialect dialect = guessDialect(mappedStatement);
            String countSql = dialect.getCountString(formatedSql);
            
            long total = calcCount(executor, mappedStatement, paramsMap, boundSql, countSql);
            
            paramsMap.put(QueryConst.TOTAL_COUNT, String.valueOf(total));
            
            if (total > 0)
            {
                String pageNoStr = paramsMap.get(QueryConst.PAGE_IDX);
                String pageSizeStr = paramsMap.get(QueryConst.PAGE_SIZE);
                
                /*
                 * 处理分页参数，如果参数不合法，将会以默认值替代,不影响功能继续执行
                 */
                int pageNo = parsePageNo(pageNoStr);
                int pageSize = parsePageSize(pageSizeStr);
                
                //计算总页数
                long pageCount = pageCount(total, pageSize);
                paramsMap.put(QueryConst.PAGE_COUNT, String.valueOf(pageCount));
                
                // 计算物理分页参数,并纠正
                long offset = calcOffset(total, pageNo, pageSize);
                
                // 获取数据库方言类来处理物理分页
                pagedSql = dialect.getLimitString(formatedSql, offset, pageSize);
                
                if (log.isDebugEnabled())
                {
                    log.debug("分页处理后:{}", pagedSql);
                }
            }
        }
        
        return pagedSql;
    }
    
    /** 
    * TODO (这里用一句话描述这个方法的作用)
    * @param executor
    * @param mappedStatement
    * @param parameters
    * @param boundSql
    * @param countSql
    * @return
    */
    private long calcCount(Executor executor, MappedStatement mappedStatement, Object parameters, BoundSql boundSql,
        String countSql)
    {
        MetaObject<BoundSql> boundSqlMeta = MetaObject.fromObject(boundSql);
        boundSqlMeta.setValue("sql", countSql);
        RowBounds rowBounds = new RowBounds(0, 1);
        
        long count = 0;
        CacheKey cacheKey = executor.createCacheKey(mappedStatement, parameters, rowBounds, boundSql);
        MetaObject<MappedStatement> mappedStatementMeta = MetaObject.fromObject(mappedStatement);
        Configuration config = unDelegate(mappedStatement.getConfiguration());
        try
        {
            // 植入假配置
            Configuration pageConfig = new PageConfiguration(config);
            mappedStatementMeta.setValue("configuration", pageConfig);
            List<Object> result =
                executor.query(mappedStatement, parameters, rowBounds, new DefaultResultHandler(), cacheKey, boundSql);
            Iterator<Object> it = result.iterator();
            if (it.hasNext())
            {
                count = (Long)it.next();
            }
        }
        catch (SQLException e)
        {
            if (log.isErrorEnabled())
            {
                log.error("计算总数时发生异常!!!", e);
            }
        }
        finally
        {
            // 还原真配置
            mappedStatementMeta.setValue("configuration", config);
        }
        
        return count;
    }
    
    /** 
    * (计算偏移量)
    * @param total 总记录数
    * @param pageNo  页号
    * @param pageSize  页容
    * @return 偏移量
    */
    private long calcOffset(long total, int pageNo, int pageSize)
    {
        int totalPage = (int)(total / pageSize);
        if (total % pageSize != 0)
        {
            totalPage++;
        }
        
        if (pageNo > totalPage)
        {
            pageNo = totalPage;
            if (log.isWarnEnabled())
            {
                StringBuilder logInfo = new StringBuilder("页面号过大：");
                logInfo.append(pageNo);
                logInfo.append("，将自动计算为最后一页。");
                log.warn(logInfo.toString());
            }
        }
        
        long offset = (pageNo - 1) * pageSize;
        
        return offset;
    }
    
    /** 
    * TODO (这里用一句话描述这个方法的作用)
    * @param pageNoStr
    * @return
    */
    private int parsePageNo(String pageNoStr)
    {
        int pageNo = QueryConst.DEFAULT_PAGE_IDX;
        
        try
        {
            pageNo = Integer.parseInt(pageNoStr);
        }
        catch (NumberFormatException e)
        {
            if (log.isWarnEnabled())
            {
                String tips = "该参数值必须能解析为数字!";
                String logInfo = mkInvldPrmLg(QueryConst.PAGE_IDX, pageNoStr, QueryConst.DEFAULT_PAGE_IDX, tips);
                log.warn(logInfo.toString());
            }
        }
        
        if (pageNo < 1)
        {
            if (log.isWarnEnabled())
            {
                String tips = "该参数值不能小于1!";
                String logInfo = mkInvldPrmLg(QueryConst.PAGE_IDX, pageNoStr, QueryConst.DEFAULT_PAGE_IDX, tips);
                log.warn(logInfo.toString());
            }
            
            pageNo = QueryConst.DEFAULT_PAGE_IDX;
        }
        
        return pageNo;
    }
    
    /** 
    * TODO (这里用一句话描述这个方法的作用)
    * @param pageSizeStr
    * @return
    */
    private int parsePageSize(String pageSizeStr)
    {
        int pageSize = QueryConst.DEFAULT_PAGE_SIZE;
        
        try
        {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        catch (NumberFormatException e)
        {
            if (log.isWarnEnabled())
            {
                String tips = "该参数值必须能解析为数字!";
                String logInfo = mkInvldPrmLg(QueryConst.PAGE_SIZE, pageSizeStr, QueryConst.DEFAULT_PAGE_SIZE, tips);
                log.warn(logInfo.toString());
            }
        }
        
        if (pageSize < 1)
        {
            if (log.isWarnEnabled())
            {
                String tips = "该参数值不能小于1!";
                String logInfo = mkInvldPrmLg(QueryConst.PAGE_SIZE, pageSizeStr, QueryConst.DEFAULT_PAGE_SIZE, tips);
                log.warn(logInfo.toString());
            }
            
            pageSize = QueryConst.DEFAULT_PAGE_SIZE;
        }
        
        return pageSize;
    }
    
    /** 
    * (计算总页数)
    * @param total 总记录数
    * @param pageNo  页号
    * @param pageSize  页容
    * @return 偏移量
    */
    private long pageCount(long total, int pageSize)
    {
        int totalPage = (int)(total / pageSize);
        if (total % pageSize != 0)
        {
            totalPage++;
        }
        
        return totalPage;
    }
    
    /** 
    * TODO (这里用一句话描述这个方法的作用)
    * @param nativeSql
    * @param paramsMap
    * @param columnMap
    * @return
    */
    private String formatSql(String nativeSql, Map<String, String> paramsMap, Map<String, String> columnMap)
    {
        // 获取查询sql语句后对其进行清理
        String cleanedSql = SqlUtil.cleanSql(nativeSql);
        
        if (log.isDebugEnabled())
        {
            log.debug("处理前SQL:{}", nativeSql);
            log.debug("清理后SQL:{}", cleanedSql);
        }
        
        // 如果sql中有like操作，就对所有的like后追加ESCAPE逃逸字符
        StringShiftUti st = new StringShiftUti(cleanedSql);
        cleanedSql = st.replace("LIKE ?", "LIKE ? ESCAPE '/'").toString();
        
        /*
         * 检测最外层SQL中是否存在排序语句
         */
        boolean hasOrder = false;
        int lastRBracketIdx = cleanedSql.lastIndexOf(')');
        if (cleanedSql.toUpperCase().indexOf("ORDER BY", lastRBracketIdx) > 0)
        {
            hasOrder = true;
        }
        
        /*
         *处理排序的字段名和排序类型
         *如果字段名不存在，在用默认的更新时间逆序来排序
         *字段存在，但排序类型参数值如果不合法，则默认按照顺序排列
         */
        StringBuilder sqlBuilder = new StringBuilder(cleanedSql);
        
        //排序字段
        String orderName = null;
        try
        {
            orderName = paramsMap.get(QueryConst.ORDER_NAME);
        }
        catch (Exception e)
        {
            log.info("传入参数无排序字段!");
        }
        
        if (null != orderName)
        {
            String orderColumn = columnMap.get(orderName);
            if (null == orderColumn)
            {
                if (log.isWarnEnabled())
                {
                    String tips = "该参数应当为实体中存在的属性名!";
                    String logInfo = mkInvldPrmLg(QueryConst.ORDER_NAME, orderName, "dateCreate", tips);
                    log.warn(logInfo);
                }
            }
            else
            {
                if (!hasOrder)
                {
                    sqlBuilder.append(" ORDER BY ");
                }
                else
                {
                    sqlBuilder.append(",");
                }
                sqlBuilder.append(orderColumn);
                
                //排序类型
                String orderType = null;
                try
                {
                    orderType = paramsMap.get(QueryConst.ORDER_TYPE);
                }
                catch (Exception e)
                {
                    log.info("传入参数无排序类型!");
                }
                
                if (null == orderType
                    || !("DESC".equalsIgnoreCase(orderType.trim()) || "ASC".equalsIgnoreCase(orderType.trim())))
                {
                    orderType = QueryConst.ORDER_TYPE_ASC;
                    
                    if (log.isWarnEnabled())
                    {
                        String tips = "该参数支持\"ASC\"|\"DESC\"大小写不敏感";
                        String logInfo =
                            mkInvldPrmLg(QueryConst.ORDER_TYPE, orderType, QueryConst.ORDER_TYPE_ASC, tips);
                        log.warn(logInfo.toString());
                    }
                }
                
                sqlBuilder.append(" ");
                sqlBuilder.append(orderType.toUpperCase());
            }
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("排序处理后:{}", sqlBuilder);
        }
        
        return sqlBuilder.toString();
    }
    
    /** 
    * (先处理排序问题;  获取当前结果集属性-列名映射; 用作排序时作转换用)
    * @param mappedStatement
    * @return
    */
    private Map<String, String> getColumnMap(MappedStatement mappedStatement)
    {
        Map<String, String> columnMap = new HashMap<String, String>();
        List<ResultMap> resultMaps = mappedStatement.getResultMaps();
        for (ResultMap resultMap : resultMaps)
        {
            List<ResultMapping> resultMappings = resultMap.getResultMappings();
            for (ResultMapping resultMapping : resultMappings)
            {
                String property = resultMapping.getProperty();
                String column = resultMapping.getColumn();
                columnMap.put(property, column);
            }
        }
        return columnMap;
    }
    
    /** 
    * TODO (这里用一句话描述这个方法的作用)
    * @param mappedStatement
    * @return
    */
    private Dialect guessDialect(MappedStatement mappedStatement)
    {
        Configuration configuration = mappedStatement.getConfiguration();
        String dialectName = configuration.getVariables().getProperty("dialect");
        Dialect dialect = Dialect.getDialect(dialectName);
        
        return dialect;
    }
    
    /** 
    * (构建因参数非法而产生的日志信息)
    * @param param  参数名
    * @param paramValue 传入值
    * @param defaultValue  替补值
    * @param tips 额外提示
    * @return
    */
    private static String mkInvldPrmLg(String param, Object paramValue, Object defaultValue, String tips)
    {
        StringBuilder logInfo = new StringBuilder("非法的\"");
        logInfo.append(param);
        logInfo.append("\"值:");
        logInfo.append(paramValue);
        logInfo.append("被传入，将使用默认值:");
        logInfo.append(defaultValue);
        logInfo.append("替代。");
        logInfo.append(tips);
        
        return logInfo.toString();
    }
    
}

class PageConfiguration extends Configuration
{
    private Configuration delegate;
    
    public PageConfiguration(Configuration delegate)
    {
        super();
        this.delegate = delegate;
    }
    
    @Override
    public void addCache(Cache cache)
    {
        delegate.addCache(cache);
    }
    
    @Override
    public void addCacheRef(String namespace, String referencedNamespace)
    {
        delegate.addCacheRef(namespace, referencedNamespace);
    }
    
    @Override
    public void addIncompleteCacheRef(CacheRefResolver incompleteCacheRef)
    {
        delegate.addIncompleteCacheRef(incompleteCacheRef);
    }
    
    @Override
    public void addIncompleteResultMap(ResultMapResolver resultMapResolver)
    {
        delegate.addIncompleteResultMap(resultMapResolver);
    }
    
    @Override
    public void addIncompleteStatement(XMLStatementBuilder incompleteStatement)
    {
        delegate.addIncompleteStatement(incompleteStatement);
    }
    
    @Override
    public void addInterceptor(Interceptor interceptor)
    {
        delegate.addInterceptor(interceptor);
    }
    
    @Override
    public void addKeyGenerator(String id, KeyGenerator keyGenerator)
    {
        delegate.addKeyGenerator(id, keyGenerator);
    }
    
    @Override
    public void addLoadedResource(String resource)
    {
        delegate.addLoadedResource(resource);
    }
    
    @Override
    public void addMappedStatement(MappedStatement ms)
    {
        delegate.addMappedStatement(ms);
    }
    
    @Override
    public <T> void addMapper(Class<T> type)
    {
        delegate.addMapper(type);
    }
    
    @Override
    public void addMappers(String packageName, Class<?> delegateType)
    {
        delegate.addMappers(packageName, delegateType);
    }
    
    @Override
    public void addMappers(String packageName)
    {
        delegate.addMappers(packageName);
    }
    
    @Override
    public void addParameterMap(ParameterMap pm)
    {
        delegate.addParameterMap(pm);
    }
    
    @Override
    public void addResultMap(ResultMap rm)
    {
        delegate.addResultMap(rm);
    }
    
    @Override
    protected void buildAllStatements()
    {
        try
        {
            Method method = delegate.getClass().getMethod("buildAllStatements");
            method.setAccessible(true);
            method.invoke(delegate);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void checkGloballyForDiscriminatedNestedResultMaps(ResultMap rm)
    {
        try
        {
            Method method =
                delegate.getClass().getMethod("checkGloballyForDiscriminatedNestedResultMaps", ResultMap.class);
            method.setAccessible(true);
            method.invoke(delegate, rm);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void checkLocallyForDiscriminatedNestedResultMaps(ResultMap rm)
    {
        try
        {
            Method method =
                delegate.getClass().getMethod("checkLocallyForDiscriminatedNestedResultMaps", ResultMap.class);
            method.setAccessible(true);
            method.invoke(delegate, rm);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    protected String extractNamespace(String statementId)
    {
        try
        {
            Method method = delegate.getClass().getMethod("extractNamespace", String.class);
            method.setAccessible(true);
            return (String)method.invoke(delegate, statementId);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public AutoMappingBehavior getAutoMappingBehavior()
    {
        return delegate.getAutoMappingBehavior();
    }
    
    @Override
    public Cache getCache(String id)
    {
        return delegate.getCache(id);
    }
    
    @Override
    public Collection<String> getCacheNames()
    {
        return delegate.getCacheNames();
    }
    
    @Override
    public Collection<Cache> getCaches()
    {
        return delegate.getCaches();
    }
    
    @Override
    public String getDatabaseId()
    {
        return delegate.getDatabaseId();
    }
    
    @Override
    public ExecutorType getDefaultExecutorType()
    {
        return delegate.getDefaultExecutorType();
    }
    
    @Override
    public Integer getDefaultStatementTimeout()
    {
        return delegate.getDefaultStatementTimeout();
    }
    
    @Override
    public Environment getEnvironment()
    {
        return delegate.getEnvironment();
    }
    
    @Override
    public Collection<CacheRefResolver> getIncompleteCacheRefs()
    {
        return delegate.getIncompleteCacheRefs();
    }
    
    @Override
    public Collection<ResultMapResolver> getIncompleteResultMaps()
    {
        return delegate.getIncompleteResultMaps();
    }
    
    @Override
    public Collection<XMLStatementBuilder> getIncompleteStatements()
    {
        return delegate.getIncompleteStatements();
    }
    
    @Override
    public JdbcType getJdbcTypeForNull()
    {
        return delegate.getJdbcTypeForNull();
    }
    
    @Override
    public KeyGenerator getKeyGenerator(String id)
    {
        return delegate.getKeyGenerator(id);
    }
    
    @Override
    public Collection<String> getKeyGeneratorNames()
    {
        return delegate.getKeyGeneratorNames();
    }
    
    @Override
    public Collection<KeyGenerator> getKeyGenerators()
    {
        return delegate.getKeyGenerators();
    }
    
    @Override
    public Set<String> getLazyLoadTriggerMethods()
    {
        return delegate.getLazyLoadTriggerMethods();
    }
    
    @Override
    public LocalCacheScope getLocalCacheScope()
    {
        return delegate.getLocalCacheScope();
    }
    
    @Override
    public MappedStatement getMappedStatement(String id, boolean validateIncompleteStatements)
    {
        return delegate.getMappedStatement(id, validateIncompleteStatements);
    }
    
    @Override
    public MappedStatement getMappedStatement(String id)
    {
        return delegate.getMappedStatement(id);
    }
    
    @Override
    public Collection<String> getMappedStatementNames()
    {
        return delegate.getMappedStatementNames();
    }
    
    @Override
    public Collection<MappedStatement> getMappedStatements()
    {
        return delegate.getMappedStatements();
    }
    
    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession)
    {
        return delegate.getMapper(type, sqlSession);
    }
    
    @Override
    public ObjectFactory getObjectFactory()
    {
        return delegate.getObjectFactory();
    }
    
    @Override
    public ObjectWrapperFactory getObjectWrapperFactory()
    {
        return delegate.getObjectWrapperFactory();
    }
    
    @Override
    public ParameterMap getParameterMap(String id)
    {
        return delegate.getParameterMap(id);
    }
    
    @Override
    public Collection<String> getParameterMapNames()
    {
        return delegate.getParameterMapNames();
    }
    
    @Override
    public Collection<ParameterMap> getParameterMaps()
    {
        return delegate.getParameterMaps();
    }
    
    @Override
    public ResultMap getResultMap(String id)
    {
        return delegate.getResultMap(id);
    }
    
    @Override
    public Collection<String> getResultMapNames()
    {
        return delegate.getResultMapNames();
    }
    
    @Override
    public Collection<ResultMap> getResultMaps()
    {
        return delegate.getResultMaps();
    }
    
    @Override
    public Map<String, XNode> getSqlFragments()
    {
        return delegate.getSqlFragments();
    }
    
    @Override
    public TypeAliasRegistry getTypeAliasRegistry()
    {
        return delegate.getTypeAliasRegistry();
    }
    
    @Override
    public TypeHandlerRegistry getTypeHandlerRegistry()
    {
        return delegate.getTypeHandlerRegistry();
    }
    
    @Override
    public Properties getVariables()
    {
        return delegate.getVariables();
    }
    
    @Override
    public boolean hasCache(String id)
    {
        return delegate.hasCache(id);
    }
    
    @Override
    public boolean hasKeyGenerator(String id)
    {
        return delegate.hasKeyGenerator(id);
    }
    
    @Override
    public boolean hasMapper(Class<?> type)
    {
        return delegate.hasMapper(type);
    }
    
    @Override
    public boolean hasParameterMap(String id)
    {
        return delegate.hasParameterMap(id);
    }
    
    @Override
    public boolean hasResultMap(String id)
    {
        return delegate.hasResultMap(id);
    }
    
    @Override
    public boolean hasStatement(String statementName, boolean validateIncompleteStatements)
    {
        return delegate.hasStatement(statementName, validateIncompleteStatements);
    }
    
    @Override
    public boolean hasStatement(String statementName)
    {
        return delegate.hasStatement(statementName);
    }
    
    @Override
    public boolean isAggressiveLazyLoading()
    {
        return delegate.isAggressiveLazyLoading();
    }
    
    @Override
    public boolean isCacheEnabled()
    {
        return delegate.isCacheEnabled();
    }
    
    @Override
    public boolean isLazyLoadingEnabled()
    {
        return delegate.isLazyLoadingEnabled();
    }
    
    @Override
    public boolean isMapUnderscoreToCamelCase()
    {
        return delegate.isMapUnderscoreToCamelCase();
    }
    
    @Override
    public boolean isMultipleResultSetsEnabled()
    {
        return delegate.isMultipleResultSetsEnabled();
    }
    
    @Override
    public boolean isUseColumnLabel()
    {
        return delegate.isUseColumnLabel();
    }
    
    @Override
    public boolean isResourceLoaded(String resource)
    {
        return delegate.isResourceLoaded(resource);
    }
    
    @Override
    public boolean isSafeResultHandlerEnabled()
    {
        return delegate.isSafeResultHandlerEnabled();
    }
    
    @Override
    public boolean isSafeRowBoundsEnabled()
    {
        return delegate.isSafeRowBoundsEnabled();
    }
    
    @Override
    public boolean isUseGeneratedKeys()
    {
        return delegate.isUseGeneratedKeys();
    }
    
    //    @Override
    //    public Executor newExecutor(Transaction transaction, ExecutorType executorType, boolean autoCommit)
    //    {
    //        return delegate.newExecutor(transaction, executorType, autoCommit);
    //    }
    
    @Override
    public Executor newExecutor(Transaction transaction, ExecutorType executorType)
    {
        return delegate.newExecutor(transaction, executorType);
    }
    
    @Override
    public Executor newExecutor(Transaction transaction)
    {
        return delegate.newExecutor(transaction);
    }
    
    @Override
    public org.apache.ibatis.reflection.MetaObject newMetaObject(Object object)
    {
        return delegate.newMetaObject(object);
    }
    
    @Override
    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject,
        BoundSql boundSql)
    {
        return delegate.newParameterHandler(mappedStatement, parameterObject, boundSql);
    }
    
    @Override
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement,
        RowBounds rowBounds, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql)
    {
        return new ResultSetHandler()
        {
            @Override
            public void handleOutputParameters(CallableStatement cs)
                throws SQLException
            {
            }
            
            @SuppressWarnings("unchecked")
            @Override
            public List<Long> handleResultSets(Statement stmt)
                throws SQLException
            {
                List<Long> count = new ArrayList<Long>();
                ResultSet rs = stmt.getResultSet();
                if (rs.next())
                {
                    count.add(rs.getLong(1));
                }
                
                return count;
            }
        };
    }
    
    @Override
    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement,
        Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql)
    {
        return delegate.newStatementHandler(executor,
            mappedStatement,
            parameterObject,
            rowBounds,
            resultHandler,
            boundSql);
    }
    
    @Override
    public void setAggressiveLazyLoading(boolean aggressiveLazyLoading)
    {
        delegate.setAggressiveLazyLoading(aggressiveLazyLoading);
    }
    
    @Override
    public void setAutoMappingBehavior(AutoMappingBehavior autoMappingBehavior)
    {
        delegate.setAutoMappingBehavior(autoMappingBehavior);
    }
    
    @Override
    public void setCacheEnabled(boolean cacheEnabled)
    {
        delegate.setCacheEnabled(cacheEnabled);
    }
    
    @Override
    public void setDatabaseId(String databaseId)
    {
        delegate.setDatabaseId(databaseId);
    }
    
    @Override
    public void setDefaultExecutorType(ExecutorType defaultExecutorType)
    {
        delegate.setDefaultExecutorType(defaultExecutorType);
    }
    
    @Override
    public void setDefaultStatementTimeout(Integer defaultStatementTimeout)
    {
        delegate.setDefaultStatementTimeout(defaultStatementTimeout);
    }
    
    @Override
    public void setEnvironment(Environment environment)
    {
        delegate.setEnvironment(environment);
    }
    
    @Override
    public void setJdbcTypeForNull(JdbcType jdbcTypeForNull)
    {
        delegate.setJdbcTypeForNull(jdbcTypeForNull);
    }
    
    @Override
    public void setLazyLoadingEnabled(boolean lazyLoadingEnabled)
    {
        delegate.setLazyLoadingEnabled(lazyLoadingEnabled);
    }
    
    @Override
    public void setLazyLoadTriggerMethods(Set<String> lazyLoadTriggerMethods)
    {
        delegate.setLazyLoadTriggerMethods(lazyLoadTriggerMethods);
    }
    
    @Override
    public void setLocalCacheScope(LocalCacheScope localCacheScope)
    {
        delegate.setLocalCacheScope(localCacheScope);
    }
    
    @Override
    public void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase)
    {
        delegate.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
    }
    
    @Override
    public void setMultipleResultSetsEnabled(boolean multipleResultSetsEnabled)
    {
        delegate.setMultipleResultSetsEnabled(multipleResultSetsEnabled);
    }
    
    @Override
    public void setObjectFactory(ObjectFactory objectFactory)
    {
        delegate.setObjectFactory(objectFactory);
    }
    
    @Override
    public void setObjectWrapperFactory(ObjectWrapperFactory objectWrapperFactory)
    {
        delegate.setObjectWrapperFactory(objectWrapperFactory);
    }
    
    @Override
    public void setSafeResultHandlerEnabled(boolean safeResultHandlerEnabled)
    {
        delegate.setSafeResultHandlerEnabled(safeResultHandlerEnabled);
    }
    
    @Override
    public void setSafeRowBoundsEnabled(boolean safeRowBoundsEnabled)
    {
        delegate.setSafeRowBoundsEnabled(safeRowBoundsEnabled);
    }
    
    @Override
    public void setUseColumnLabel(boolean useColumnLabel)
    {
        delegate.setUseColumnLabel(useColumnLabel);
    }
    
    @Override
    public void setUseGeneratedKeys(boolean useGeneratedKeys)
    {
        delegate.setUseGeneratedKeys(useGeneratedKeys);
    }
    
    @Override
    public void setVariables(Properties variables)
    {
        delegate.setVariables(variables);
    }
    
}
