package cn.com.eju.deal.base.linkage.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.eju.deal.base.controller.BaseController;
import cn.com.eju.deal.base.helper.LogHelper;
import cn.com.eju.deal.base.linkage.service.LinkageService;
import cn.com.eju.deal.core.support.ResultData;

/**   
* 城市、行政区、板块 联动
* @author (li_xiaodong)
* @date 2016年3月20日 下午12:29:31
*/
@RestController
@RequestMapping(value = "linkages")
public class LinkageController extends BaseController
{
    
    /**
    * 日志
    */
    private final LogHelper logger = LogHelper.getLogger(this.getClass());
    
    @Resource(name = "linkageService")
    private LinkageService linkageService;
    
    /** 
     * 获取城市list
     * @param
     * @return
     */
    @RequestMapping(value = "/city", method = RequestMethod.GET)
    public String getCityList()
    {
        
        //查询
        ResultData<?> resultData = linkageService.getCityList();
        
        return resultData.toString();
    }
    
    /** 
    * 根据城市CityNo获取其行政区List
    * @param cityNo
    * @return
    */
    @RequestMapping(value = "/district/{cityNo}", method = RequestMethod.GET)
    public String getDistrictListByCityNo(@PathVariable String cityNo)
    {
        
        //查询
        ResultData<?> resultData = linkageService.getDistrictListByCityNo(cityNo);
        
        return resultData.toString();
    }
    
    /** 
    *根据行政区DistrictNo获取其板块List
    * @param cityNo
    * @return
    */
    @RequestMapping(value = "/area/{districtNo}", method = RequestMethod.GET)
    public String getAreaListByDistrictNo(@PathVariable String districtNo)
    {
        
        //查询
        ResultData<?> resultData = linkageService.getAreaListByDistrictNo(districtNo);
        
        return resultData.toString();
    }
    
}
