package cn.com.eju.deal.base.code.model;

import java.util.List;

import cn.com.eju.deal.core.model.BaseModel;

/**   
* WS配置实体
* @author (li_xiaodong)
* @date 2015年12月25日 下午4:20:26
*/
public class WsWebConfig extends BaseModel
{
    /**
    * TODO(用一句话描述这个变量表示什么)
    */ 
    private static final long serialVersionUID = 180876739333028672L;

    private Integer wsId;
    
    private String wsNo;
    
    private String wsKey;
    
    private Integer wsPwdVerifyFlag;
    
    private String wsUserName;
    
    private String wsUserPwd;
    
    private Integer wsIpVerifyFlag;
    
    private String wsDesc;
    
    //IPRange
    private List<IpWhite> wsIPRange;
    
    public Integer getWsId()
    {
        return wsId;
    }
    
    public void setWsId(Integer wsId)
    {
        this.wsId = wsId;
    }
    
    public String getWsKey()
    {
        return wsKey;
    }
    
    public void setWsKey(String wsKey)
    {
        this.wsKey = wsKey == null ? null : wsKey.trim();
    }
    
    public Integer getWsPwdVerifyFlag()
    {
        return wsPwdVerifyFlag;
    }
    
    public void setWsPwdVerifyFlag(Integer wsPwdVerifyFlag)
    {
        this.wsPwdVerifyFlag = wsPwdVerifyFlag;
    }
    
    public String getWsUserName()
    {
        return wsUserName;
    }
    
    public void setWsUserName(String wsUserName)
    {
        this.wsUserName = wsUserName == null ? null : wsUserName.trim();
    }
    
    public String getWsUserPwd()
    {
        return wsUserPwd;
    }
    
    public void setWsUserPwd(String wsUserPwd)
    {
        this.wsUserPwd = wsUserPwd == null ? null : wsUserPwd.trim();
    }
    
    public Integer getWsIpVerifyFlag()
    {
        return wsIpVerifyFlag;
    }
    
    public void setWsIpVerifyFlag(Integer wsIpVerifyFlag)
    {
        this.wsIpVerifyFlag = wsIpVerifyFlag;
    }
    
    public String getWsDesc()
    {
        return wsDesc;
    }
    
    public void setWsDesc(String wsDesc)
    {
        this.wsDesc = wsDesc == null ? null : wsDesc.trim();
    }
    
    public String getWsNo()
    {
        return wsNo;
    }
    
    public void setWsNo(String wsNo)
    {
        this.wsNo = wsNo == null ? null : wsNo.trim();
    }
    
    public List<IpWhite> getWsIPRange()
    {
        return wsIPRange;
    }
    
    public void setWsIPRange(List<IpWhite> wsIPRange)
    {
        this.wsIPRange = wsIPRange;
    }
    
}