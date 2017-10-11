package cn.com.eju.deal.base.dto.linkage;

import java.io.Serializable;


/**   
* 行政区
* @author (li_xiaodong)
* @date 2016年3月30日 下午5:04:54
*/
public class DistrictDto implements Serializable
{
    
    /**
    * 
    */ 
    private static final long serialVersionUID = 6147720065150148976L;

    private String cityNo;
    
    private String cityName;
    
    private String districtNo;
    
    private String districtName;
    
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
    
}