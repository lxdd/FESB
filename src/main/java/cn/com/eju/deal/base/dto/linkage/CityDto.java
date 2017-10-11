package cn.com.eju.deal.base.dto.linkage;

import java.io.Serializable;


public class CityDto implements Serializable
{
    
    /**
    * 
    */ 
    private static final long serialVersionUID = -3326664045303196856L;

    private String provinceName;
    
    private String provinceNo;
    
    private String cityName;
    
    private String cityNo;
    
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
    
}