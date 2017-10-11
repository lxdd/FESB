package cn.com.eju.deal.base.dto.log;

import cn.com.eju.deal.core.model.BaseModel;

/**   
* ErrorLog DTO
* @author (li_xiaodong)
* @date 2016年2月17日 下午4:42:35
*/
public class ErrorLogDto extends BaseModel
{
    
    /**
    * 
    */ 
    private static final long serialVersionUID = -6512376401452706285L;

    private Integer id;
    
    private String systemName;
    
    private String moduleName;
    
    private String className;
    
    private String methodName;
    
    private String parameter;
    
    private String ipAddress;
    
    private String exceptionInfo;
    
    private String description;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getSystemName()
    {
        return systemName;
    }
    
    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }
    
    public String getModuleName()
    {
        return moduleName;
    }
    
    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
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
    
    public String getIpAddress()
    {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }
    
    public String getExceptionInfo()
    {
        return exceptionInfo;
    }
    
    public void setExceptionInfo(String exceptionInfo)
    {
        this.exceptionInfo = exceptionInfo;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
}
