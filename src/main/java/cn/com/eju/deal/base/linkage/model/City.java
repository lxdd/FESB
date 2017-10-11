package cn.com.eju.deal.base.linkage.model;

import java.util.Date;

public class City
{
    private Integer id;
    
    private String provinceName;
    
    private String provinceNo;
    
    private String cityName;
    
    private String cityNo;
    
    private Integer flagTrashed;
    
    private Integer flagDeleted;
    
    private Integer state;
    
    private Date moddate;
    
    private Date exDate;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getProvinceName()
    {
        return provinceName;
    }
    
    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }
    
    public String getProvinceNo()
    {
        return provinceNo;
    }
    
    public void setProvinceNo(String provinceNo)
    {
        this.provinceNo = provinceNo == null ? null : provinceNo.trim();
    }
    
    public String getCityName()
    {
        return cityName;
    }
    
    public void setCityName(String cityName)
    {
        this.cityName = cityName == null ? null : cityName.trim();
    }
    
    public String getCityNo()
    {
        return cityNo;
    }
    
    public void setCityNo(String cityNo)
    {
        this.cityNo = cityNo == null ? null : cityNo.trim();
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
    
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
    
    public Date getModdate()
    {
        return moddate;
    }
    
    public void setModdate(Date moddate)
    {
        this.moddate = moddate;
    }
    
    public Date getExDate()
    {
        return exDate;
    }
    
    public void setExDate(Date exDate)
    {
        this.exDate = exDate;
    }
}