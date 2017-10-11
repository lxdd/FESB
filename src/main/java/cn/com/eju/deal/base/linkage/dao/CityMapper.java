package cn.com.eju.deal.base.linkage.dao;

import java.util.List;

import cn.com.eju.deal.base.linkage.model.City;
import cn.com.eju.deal.core.dao.IDao;

public interface CityMapper extends IDao<City>
{
    /** 
    * 获取所有城市
    * @return
    */
    List<City> getAll();
}