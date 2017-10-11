package cn.com.eju.deal.base.linkage.model;

import java.util.Date;

public class Province
{
    private Integer id;
    
    private String provinceName;
    
    private String provinceNo;
    
    private Date modeDate;
    
    private Date exDate;
    
    private Integer state;
    
    private Integer flagTrashed;
    
    private Integer flagDeleted;
    
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
    
    public Date getModeDate()
    {
        return modeDate;
    }
    
    public void setModeDate(Date modeDate)
    {
        this.modeDate = modeDate;
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
}