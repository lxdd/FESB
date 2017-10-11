package cn.com.eju.deal.base.persistence.plugin;

import java.sql.Connection;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.eju.deal.base.log.AuditLogUtil;
import cn.com.eju.deal.core.enums.FinalDefine;
import cn.com.eju.deal.core.reflect.MetaObject;
import cn.com.eju.deal.core.util.SqlUtil;

/**   
* 插入审计日志
* @author (li_xiaodong)
* @date 2015年10月21日 下午1:53:52
*/
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class AuditLogPlugin extends BasePlugin implements FinalDefine
{
    /* 日志 */
    private static final Logger logger = LoggerFactory.getLogger(AuditLogPlugin.class);
    
    @Override
    public Object intercept(Invocation invocation)
        throws Throwable
    {
        try
        {
            StatementHandler statementHandler = (StatementHandler)unDelegate(unProxy(invocation.getTarget()));
            MappedStatement mappedStatement =
                (MappedStatement)MetaObject.fromObject(statementHandler).getValue("mappedStatement");
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            BoundSql boundSql = statementHandler.getBoundSql();
            if (SqlCommandType.SELECT != sqlCommandType)
            {
                
                AuditLogUtil log = AuditLogUtil.getInstance();
                
                //校验审计日志开关, true ： 记录  false : 不记录
                if (!log.isOpenAuditLog(mappedStatement.getBoundSql(boundSql.getParameterObject()).getSql()))
                {
                    return invocation.proceed();
                }
                
                //当前操作员
                int userId = -1;
                
                doAuditLog(log, userId, mappedStatement, boundSql.getParameterObject());
            }
        }
        catch (Exception e)
        {
            logger.error("全局拦截记录操作表日志失败!", e.getMessage());
        }
        
        return invocation.proceed();
    }
    
    /**
     * <p>记录审计日志。</p>
     *
     * @param AuditLogUtil log
     * @param loginUserId loginUserId
     * @param mappedStatement mappedStatement
     * @param params params
     */
    private void doAuditLog(AuditLogUtil log, int loginUserId, MappedStatement mappedStatement, Object params)
    {
        String id = mappedStatement.getId();
        if (!id.startsWith("com.eju.deal.core.log.dao.AuditLogMapper")
            && !id.startsWith("cn.com.eju.deal.core.log.dao.AuditLogMapper"))
        {
            String sql = mappedStatement.getBoundSql(params).getSql();
            int prefixLen = "cn.com.eju.deal.".length();
            String app = id.substring(prefixLen, id.indexOf('.', prefixLen + 1));
            String className = id.substring(0, id.lastIndexOf('.'));
            String methodName = id.substring(id.lastIndexOf('.') + 1);
            
            String iPAddress = "";
            
            //入审计库
            log.audit(loginUserId,
                app,
                "全局拦截操作表日志",
                SqlUtil.cleanSql(sql),
                String.valueOf(params),
                "",
                className,
                methodName,
                iPAddress);
            
        }
    }
    
}
