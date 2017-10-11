package cn.com.eju.deal.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.eju.deal.base.controller.BaseController;
import cn.com.eju.deal.base.helper.LogHelper;
import cn.com.eju.deal.base.support.SystemParam;
import cn.com.eju.deal.core.support.ResultData;

/**   
* 公用controller
* @author (li_xiaodong)
* @date 2015年11月29日 下午5:15:09
*/
@RestController
@RequestMapping("/commons")
public class CommonController extends BaseController
{
    
    /**
     * 日志
     */
    private final LogHelper logger = LogHelper.getLogger(this.getClass());
    
    /** 
     * 刷新缓存
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public String refresh(HttpServletRequest request, ModelMap mop)
    {
        
        //构建返回
        ResultData<String> resultData = new ResultData<>();
        
        try
        {
            logger.info("刷新配置");
            
            SystemParam.refreshCodeMap();
        }
        catch (Exception e)
        {
            resultData.setFail();
            logger.error("common", "CommonController", "refresh", "", null, "", "刷新配置失败", e);
        }
        return resultData.toString();
        
    }
    
}
