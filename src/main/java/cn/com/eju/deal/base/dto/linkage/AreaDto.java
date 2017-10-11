package cn.com.eju.deal.base.dto.linkage;

import java.io.Serializable;


/**   
* 板块
* @author (li_xiaodong)
* @date 2016年3月30日 下午5:05:36
*/
public class AreaDto implements Serializable
{
    
    /**
    * 
    */ 
    private static final long serialVersionUID = -5524596452028551274L;

    private String cityNo;
    
    private String cityName;
    
    private String districtNo;
    
    private String districtName;
    
    private String areaName;
    
    private String areaNo;
    
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
    
    public String getAreaName()
    {
        return areaName;
    }
    
    public void setAreaName(String areaName)
    {
        this.areaName = areaName == null ? null : areaName.trim();
    }
    
    public String getAreaNo()
    {
        return areaNo;
    }
    
    public void setAreaNo(String areaNo)
    {
        this.areaNo = areaNo == null ? null : areaNo.trim();
    }
    
}