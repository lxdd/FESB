package cn.com.eju.deal.base.log.model;

import cn.com.eju.deal.core.model.BaseModel;

/**   
* <p>操作日志Model。</p>
* @author (li_xiaodong)
* @date 2015年10月23日 下午7:28:22
*/
public class AuditLog extends BaseModel
{
    private Integer id;
    
    private String className;
    
    private String methodName;
    
    private String parameter;
    
    private String sqlContent;
    
    private String operateContent;
    
    private String ipAddress;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getClassName()
    {
        return className;
    }
    
    public void setClassName(String className)
    {
        this.className = className;
    }
    
    public String getMethodName()
    {
        return methodName;
    }
    
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
    public String getParameter()
    {
        return parameter;
    }
    
    public void setParameter(String parameter)
    {
        this.parameter = parameter;
    }
    
    public String getSqlContent()
    {
        return sqlContent;
    }
    
    public void setSqlContent(String sqlContent)
    {
        this.sqlContent = sqlContent;
    }
    
    public String getOperateContent()
    {
        return operateContent;
    }
    
    public void setOperateContent(String operateContent)
    {
        this.operateContent = operateContent;
    }
    
    public String getIpAddress()
    {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }
    
    @Override
    public String toString()
    {
        return "AuditLog [id=" + id + ", className=" + className + ", methodName=" + methodName + ", parameter="
            + parameter + ", sqlContent=" + sqlContent + ", operateContent=" + operateContent + ", ipAddress="
            + ipAddress + "]";
    }
    
}
