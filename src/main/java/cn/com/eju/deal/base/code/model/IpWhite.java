package cn.com.eju.deal.base.code.model;

import java.util.Date;

/**
 * ip白名单
 * @author Lidong Cao
 *
 */
public class IpWhite
{
    private Integer id;
    
    private Integer appId;
    
    private Long ipStartInt;
    
    private String ipStartStr;
    
    private Long ipEndInt;
    
    private String ipEndStr;
    
    private Date dateCreate;
    
    private String delFlag;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public Long getIpStartInt()
    {
        return ipStartInt;
    }
    
    public void setIpStartInt(Long ipStartInt)
    {
        this.ipStartInt = ipStartInt;
    }
    
    public String getIpStartStr()
    {
        return ipStartStr;
    }
    
    public void setIpStartStr(String ipStartStr)
    {
        this.ipStartStr = ipStartStr;
    }
    
    public Long getIpEndInt()
    {
        return ipEndInt;
    }
    
    public void setIpEndInt(Long ipEndInt)
    {
        this.ipEndInt = ipEndInt;
    }
    
    public String getIpEndStr()
    {
        return ipEndStr;
    }
    
    public void setIpEndStr(String ipEndStr)
    {
        this.ipEndStr = ipEndStr;
    }
    
    public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }
}
