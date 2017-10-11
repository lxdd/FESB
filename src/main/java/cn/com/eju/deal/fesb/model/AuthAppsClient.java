package cn.com.eju.deal.fesb.model;

/**   
* 认证Model
* @author TODO (创建人)
* @date 2016年9月23日 下午3:20:38
*/
public class AuthAppsClient
{
    private Integer appId;
    
    private String appName;
    
    private String appKey;
    
    private String appSecret;
    
    private Boolean authFlag;
    
    private Boolean ipbwFlag;
    
    //
    private String resourceCode;
    
    private String resourceUrl;
    
    private String systemAddr;
    
    public Integer getAppId()
    {
        return appId;
    }
    
    public void setAppId(Integer appId)
    {
        this.appId = appId;
    }
    
    public String getAppName()
    {
        return appName;
    }
    
    public void setAppName(String appName)
    {
        this.appName = appName == null ? null : appName.trim();
    }
    
    public String getAppKey()
    {
        return appKey;
    }
    
    public void setAppKey(String appKey)
    {
        this.appKey = appKey == null ? null : appKey.trim();
    }
    
    public String getAppSecret()
    {
        return appSecret;
    }
    
    public void setAppSecret(String appSecret)
    {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }
    
    public Boolean getAuthFlag()
    {
        return authFlag;
    }
    
    public void setAuthFlag(Boolean authFlag)
    {
        this.authFlag = authFlag;
    }
    
    public Boolean getIpbwFlag()
    {
        return ipbwFlag;
    }
    
    public void setIpbwFlag(Boolean ipbwFlag)
    {
        this.ipbwFlag = ipbwFlag;
    }
    
    public String getResourceCode()
    {
        return resourceCode;
    }
    
    public void setResourceCode(String resourceCode)
    {
        this.resourceCode = resourceCode;
    }
    
    public String getResourceUrl()
    {
        return resourceUrl;
    }
    
    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }
    
    public String getSystemAddr()
    {
        return systemAddr;
    }
    
    public void setSystemAddr(String systemAddr)
    {
        this.systemAddr = systemAddr;
    }
    
}