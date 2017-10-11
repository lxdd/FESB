package cn.com.eju.deal.base.linkage.model;

import java.util.Date;

/**   
* 行政区
* @author (li_xiaodong)
* @date 2016年3月30日 下午5:04:54
*/
public class District
{
    private Integer id;
    
    private String cityNo;
    
    private String cityName;
    
    private String districtNo;
    
    private String districtName;
    
    private Date moddate;
    
    private Integer flagTrashed;
    
    private Integer flagDeleted;
    
    private Date exDate;
    
    private Integer state;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getCityNo()
    {
        return cityNo;
    }
    
    public void setCityNo(String cityNo)
    {
        this.cityNo = cityNo == null ? null : cityNo.trim();
    }
    
    public String getCityName()
    {
        return cityName;
    }
    
    public void setCityName(String cityName)
    {
        this.cityName = cityName == null ? null : cityName.trim();
    }
    
    public String getDistrictNo()
    {
        return districtNo;
    }
    
    public void setDistrictNo(String districtNo)
    {
        this.districtNo = districtNo == null ? null : districtNo.trim();
    }
    
    public String getDistrictName()
    {
        return districtName;
    }
    
    public void setDistrictName(String districtName)
    {
        this.districtName = districtName == null ? null : districtName.trim();
    }
    
    public Date getModdate()
    {
        return moddate;
    }
    
    public void setModdate(Date moddate)
    {
        this.moddate = moddate;
    }
    
    public Integer getFlagTrashed()
    {
        return flagTrashed;
    }
    
    public void setFlagTrashed(Integer flagTrashed)
    {
        this.flagTrashed = flagTrashed;
    }
    
    public Integer getFlagDeleted()
    {
        return flagDeleted;
    }
    
    public void setFlagDeleted(Integer flagDeleted)
    {
        this.flagDeleted = flagDeleted;
    }
    
    public Date getExDate()
    {
        return exDate;
    }
    
    public void setExDate(Date exDate)
    {
        this.exDate = exDate;
    }
    
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
}