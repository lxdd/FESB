package cn.com.eju.deal.base.log;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;

import cn.com.eju.deal.base.helper.LogHelper;
import cn.com.eju.deal.base.log.model.AuditLog;
import cn.com.eju.deal.core.dao.IDao;
import cn.com.eju.deal.core.enums.FinalDefine;
import cn.com.eju.deal.core.helper.SpringConfigHelper;
import cn.com.eju.deal.core.model.BaseModel;
import cn.com.eju.deal.core.util.SqlUtil;
import cn.com.eju.deal.core.util.StringUtil;

/**   
* 审计日志配置文件相关的工具类。
* @author (li_xiaodong)
* @date 2015年10月23日 下午7:28:51
*/
public class AuditLogUtil
{
    
    /** 日志 **/
    private static LogHelper logger = LogHelper.getLogger(AuditLogUtil.class);
    
    /** 审志日志配置信息 **/
    private static Map<String, Object> AuditLogTableMap = new Hashtable<String, Object>();
    
    /** 配置文件 **/
    private static Properties prop = new Properties();
    
    /** 审计日志对象  **/
    private static AuditLogUtil audiLog = null;
    
    //本地配置文件
    private static final String BUNDLE_NAME = "auditlog";
    
    //    static
    //    {
    //        //读取配置文件
    //        try
    //        {
    //            String url = StringUtil.joinStr(Context.currentContext().getBasePath(), FinalDefine.AUDIT_LOG_LOCAL_PATH);
    //            InputStream in = new FileInputStream(url);
    //            prop.load(in);
    //            //遍历配置文件内容
    //            loadAll();
    //        }
    //        catch (IOException e)
    //        {
    //            log.error("审计日志配置文件(auditlog.properties)读取失败!");
    //        }
    //        catch (Exception e1)
    //        {
    //            log.error("审计日志配置文件(auditlog.properties)解析异常!");
    //        }
    //    }
    
    static
    {
        try
        {
            //开启本地读取模式
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
            Enumeration<String> e = bundle.getKeys();
            while (e.hasMoreElements())
            {
                String key = e.nextElement();
                prop.put(key, bundle.getString(key));
            }
            
            //遍历配置文件内容
            loadAll();
        }
        catch (Exception e1)
        {
            logger.error("base.log", "AuditLogUtil", "static", "", null, "", "审计日志配置文件(auditlog.properties)解析异常!", e1);
        }
        
    }
    
    /**.
     * 实现单例 
     * 
     * @return 审计日志对象
     */
    public static AuditLogUtil getInstance()
    {
        if (null == audiLog)
        {
            audiLog = new AuditLogUtil();
        }
        return audiLog;
    }
    
    /**.
     * 遍历配置文件中内容并构造成Map
     * 
     * @return Map 
     */
    private static void loadAll()
    {
        if (null != prop)
        {
            for (Iterator<Entry<Object, Object>> it = prop.entrySet().iterator(); it.hasNext();)
            {
                Entry<Object, Object> entry = it.next();
                AuditLogTableMap.put(StringUtil.toLowerStr(entry.getKey().toString()), entry.getValue());
            }
        }
    }
    
    /**.
     * 打开审计日志开关
     * 
     * @param tableName 表名
     * @return 操作结果  true:成功  fasle:失败
     */
    public boolean openAuditLog(String tableName)
    {
        if (StringUtil.isEmpty(tableName))
        {
            logger.error("打开审计日志失败,原因为tablename is null");
            return false;
        }
        //保证key全部为小写
        return setMapValue(StringUtil.toLowerStr(tableName), FinalDefine.AUDIT_LOG_OPEN);
    }
    
    /**.
     * 关闭审计日志开关
     * 
     * @param tableName 表名
     * @return 操作结果  true:成功  fasle:失败
     */
    public boolean closeAuditLog(String tableName)
    {
        if (StringUtil.isEmpty(tableName))
        {
            logger.error("审计日志关闭失败,原因为tablename is null");
            return false;
        }
        //保证key全部为小写
        return setMapValue(StringUtil.toLowerStr(tableName), FinalDefine.AUDIT_LOG_CLOSE);
    }
    
    /**.
     * 动态修改审计日志开关
     * 
     * @param key   表名
     * @param value 日志开关(0:关,1:开)
     * @return true : 成功  false ： 失败
     */
    private boolean setMapValue(String key, Object value)
    {
        if (StringUtil.isNotEmpty(key) && null != value)
        {
            AuditLogTableMap.put(key, value);
            return true;
        }
        return false;
    }
    
    /**.
     * 通过表名获取审计日志开关值
     * 
     * @param key 表名
     * @return 日志开关(0:关,1:开)
     */
    private Object getMapValueByKey(String key)
    {
        if (StringUtil.isNotEmpty(key))
        {
            //获取开关内容并转为小写
            return AuditLogTableMap.get(key);
        }
        return null;
    }
    
    /**.
     * 解析SQL判断其表名对应的审计日志状态
     * 
     * @param sql 执行的SQL
     * @return 判断结果(true:成功, false:失败)
     */
    public boolean isOpenAuditLog(String sql)
    {
        if (StringUtil.isEmpty(sql))
        {
            return false;
        }
        return isOpenAuditLogByKey(getTableName(sql));
    }
    
    /**.
     * 通过表名判断对应的审计日志开关状态
     * 
     * @param key 表名
     * @return 判断结果  true:成功 ,flase:失败
     */
    private boolean isOpenAuditLogByKey(String key)
    {
        if (StringUtil.isEmpty(key))
        {
            return false;
        }
        //字符串格式化:1、去除两边空格,2、进行小写转换
        key = StringUtil.toLowerStr(StringUtil.safeTrim(key));
        
        String flag = StringUtil.toStringWithEmpty(getMapValueByKey(key));
        if (StringUtil.isEmpty(flag))
        {
            logger.error("获取表[" + key + "]审计日志开关信息失败!");
            return false;
        }
        //审计日志状态判断
        if (FinalDefine.AUDIT_LOG_OPEN.equals(flag))
        {
            return true;
        }
        return false;
    }
    
    /**.
     * 解析SQL获取表名
     * 
     * @param sql 执行SQL
     * @return 表名
     */
    public String getTableName(String sql)
    {
        //取得操作的表名
        String tableName = null;
        if (sql.indexOf("INSERT INTO") >= 0)
        {
            tableName = sql.substring(sql.indexOf("INSERT INTO") + "INSERT INTO".length());
            tableName = tableName.substring(0, tableName.indexOf("("));
        }
        else if (sql.indexOf("UPDATE") >= 0)
        {
            tableName = sql.substring(sql.indexOf("UPDATE") + "UPDATE".length());
            if (sql.indexOf(" AS ") >= 0)
            {
                tableName = tableName.substring(0, tableName.indexOf(" AS "));
            }
            else
            {
                tableName = tableName.substring(0, tableName.indexOf("SET"));
            }
            
        }
        else if (sql.indexOf("DELETE") >= 0)
        {
            tableName = sql.substring(sql.indexOf("DELETE") + "DELETE".length());
            if (sql.indexOf("FROM") >= 0)
            {
                tableName = tableName.substring(0, tableName.indexOf("FROM"));
            }
            else if (sql.indexOf("WHERE") >= 0)
            {
                tableName = tableName.substring(0, tableName.indexOf("WHERE"));
            }
        }
        else
        {
            tableName = "COMMON_T";
        }
        
        return tableName;
    }
    
    /**
     * 记录审计日志
     *
     * @param loginUserId 登录用户ID
     * @param app 所属应用
     * @param description 审计描述
     * @param sql 操作SQL文
     * @param param SQL文参数
     * @param after 修改后对象
     * @param className 调用对象的类名
     * @param methodName 调用对象的方法名
     *
     */
    public void audit(int loginUserId, String app, String description, String sql, String param, Object after, String className, String methodName, String iPAddress)
    {
        //取得操作的表名 
        //提取解析表名方法
        String tableName = getTableName(sql);
        
        //取得系统表数据
        IDao<BaseModel> auditLogMapper = SpringConfigHelper.getDaoBeanByDaoClassName("auditLogMapper");
        
        AuditLog auditLog = new AuditLog();
        
        //类名
        auditLog.setClassName(className);
        //方法名
        auditLog.setMethodName(methodName);
        //参数
        auditLog.setParameter(String.valueOf(param));
        //SQL文
        auditLog.setSqlContent(SqlUtil.cleanSql(sql));
        //操作内容
        auditLog.setOperateContent("");
        //IP地址
        auditLog.setIpAddress(iPAddress);
        //当前操作员
        auditLog.setUserIdCreate(loginUserId);
        
        try
        {
            auditLogMapper.create(auditLog);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("全局操作表日志入库失败!", e);
        }
        
        //登录审计日志
        int cnt = auditLogMapper.create(auditLog);
        
        if (cnt == 0)
        {
            logger.error("登录审计日志失败");
        }
    }
    
    /**
     * 记录Error日志
     *
     * @param loginUserId 登录用户ID
     * @param app 所属应用
     * @param description 审计描述
     * @param sql 操作SQL文
     * @param param SQL文参数
     * @param after 修改后对象
     * @param className 调用对象的类名
     * @param methodName 调用对象的方法名
     *
     */
    //    public void addErrorLog(String moduleName, String className, String methodName, String parameter, String ipAddress,
    //        String exceptionInfo, String description, Integer userIdOperate)
    //    {
    //        //取得系统表数据
    //        IDao<BaseModel> errorLogMapper = SpringConfigHelper.getDaoBeanByDaoClassName("logErrorMapper");
    //        
    //        ErrorLog logError = new ErrorLog();
    //        
    //        logError.setModuleName(moduleName);
    //        logError.setClassName(className);
    //        logError.setMethodName(methodName);
    //        logError.setParameter(parameter);
    //        
    //        logError.setIpAddress(ipAddress);
    //        logError.setExceptionInfo(exceptionInfo);
    //        
    //        logError.setDescription(description);
    //        
    //        logError.setUserIdCreate(userIdOperate);
    //        
    //        //登录审计日志
    //        int cnt = errorLogMapper.create(logError);
    //        
    //        if (cnt == 0)
    //        {
    //            log.error("登录日志失败");
    //        }
    //    }
}
