package cn.com.eju.deal.base.dto.linkage;

import java.io.Serializable;


public class ProvinceDto implements Serializable
{
    
    /**
    * 
    */ 
    private static final long serialVersionUID = -1895600471430694612L;

    private String provinceName;
    
    private String provinceNo;
    
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
    
}