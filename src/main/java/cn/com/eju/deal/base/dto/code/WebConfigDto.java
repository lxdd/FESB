package cn.com.eju.deal.base.dto.code;

import java.util.Date;

import cn.com.eju.deal.core.model.BaseModel;

/**   
* 数据配置类
* @author (li_xiaodong)
* @date 2016年3月20日 下午12:25:37
*/
public class WebConfigDto extends BaseModel
{
    /**
    * TODO(用一句话描述这个变量表示什么)
    */ 
    private static final long serialVersionUID = 4074686802466073792L;

    private Integer webConfigId;
    
    private String webConfigName;
    
    private String webConfigValue;
    
    private String webConfigDesc;
    
    private Date dateCreate;
    
    private Integer userIdCreate;
    
    private String delFlag;
    
    /** 
    * 获取数据配置Id
    * @return
    */
    public Integer getWebConfigId()
    {
        return webConfigId;
    }
    
    /** 
    * 设置数据配置Id
    * @param webConfigId
    */
    public void setWebConfigId(Integer webConfigId)
    {
        this.webConfigId = webConfigId;
    }
    
    /** 
    * 获取数据配置名称
    * @return
    */
    public String getWebConfigName()
    {
        return webConfigName;
    }
    
    /** 
    * 设置数据配置名称
    * @param webConfigName
    */
    public void setWebConfigName(String webConfigName)
    {
        this.webConfigName = webConfigName == null ? null : webConfigName.trim();
    }
    
    /** 
    * 获取数据配置值
    * @return
    */
    public String getWebConfigValue()
    {
        return webConfigValue;
    }
    
    /** 
    * 设置数据配置值
    * @param webConfigValue
    */
    public void setWebConfigValue(String webConfigValue)
    {
        this.webConfigValue = webConfigValue == null ? null : webConfigValue.trim();
    }
    
    /** 
    * 获取数据配置描述
    * @return
    */
    public String getWebConfigDesc()
    {
        return webConfigDesc;
    }
    
    /** 
    * 设置数据配置描述
    * @param webConfigDesc
    */
    public void setWebConfigDesc(String webConfigDesc)
    {
        this.webConfigDesc = webConfigDesc == null ? null : webConfigDesc.trim();
    }
    
    public Date getDateCreate()
    {
        return dateCreate;
    }
    
    public void setDateCreate(Date dateCreate)
    {
        this.dateCreate = dateCreate;
    }
    
    public Integer getUserIdCreate()
    {
        return userIdCreate;
    }
    
    public void setUserIdCreate(Integer userIdCreate)
    {
        this.userIdCreate = userIdCreate;
    }
    
    public String getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }
}