package cn.com.eju.deal.base.linkage.dao;

import java.util.List;

import cn.com.eju.deal.base.linkage.model.Area;
import cn.com.eju.deal.core.dao.IDao;

public interface AreaMapper extends IDao<Area>
{
    /** 
    * 根据行政区DistrictNo获取其板块
    * @return
    */
    List<Area> getAreaByDistrictNo(String districtNo);
}