package cn.com.eju.deal.base.linkage.dao;

import java.util.List;

import cn.com.eju.deal.base.linkage.model.District;
import cn.com.eju.deal.core.dao.IDao;

public interface DistrictMapper extends IDao<District>
{
    
    /** 
    * 根据城市CityNo获取其行政区List
    * @return
    */
    List<District> getDistrictByCityNo(String cityNo);
}