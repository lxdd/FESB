package cn.com.eju.deal.base.code.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.eju.deal.base.code.service.WebConfigService;
import cn.com.eju.deal.base.controller.BaseController;
import cn.com.eju.deal.base.helper.LogHelper;
import cn.com.eju.deal.core.support.ResultData;

/**   
* 系统配置
* @author (li_xiaodong)
* @date 2016年3月20日 下午12:29:31
*/
@RestController
@RequestMapping(value = "webconfigs")
public class WebConfigController extends BaseController
{
    
    /**
    * 日志
    */
    private final LogHelper logger = LogHelper.getLogger(this.getClass());
    
    @Resource(name = "webConfigService")
    private WebConfigService webConfigService;
    
    /** 
     * 查询-对象
     * @param id
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAll()
    {
        Map<?, ?> queryParam = new HashMap<String, Object>();
        
        //查询
        ResultData<?> resultData = new ResultData<>();
        try
        {
            resultData = webConfigService.queryList(queryParam);
        }
        catch (Exception e)
        {
            resultData.setFail();
            logger.error("base", "WebConfigController", "getAll", "", null, "", "", e);
        }
        
        return resultData.toString();
    }
    
}
