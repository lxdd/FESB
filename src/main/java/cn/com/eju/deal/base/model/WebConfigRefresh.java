package cn.com.eju.deal.base.model;

import cn.com.eju.deal.core.model.BaseModel;

/**   
* 配置刷新服务器IP
* @author (li_xiaodong)
* @date 2015年12月16日 下午9:05:07
*/
public class WebConfigRefresh extends BaseModel
{
    private Integer id;
    
    private String ip;
    
    private String ipdesc;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getIp()
    {
        return ip;
    }
    
    public void setIp(String ip)
    {
        this.ip = ip == null ? null : ip.trim();
    }
    
    public String getIpdesc()
    {
        return ipdesc;
    }
    
    public void setIpdesc(String ipdesc)
    {
        this.ipdesc = ipdesc == null ? null : ipdesc.trim();
    }
    
}