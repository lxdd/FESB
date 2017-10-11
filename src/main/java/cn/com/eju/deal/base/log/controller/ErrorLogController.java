package cn.com.eju.deal.base.log.controller;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.eju.deal.base.controller.BaseController;
import cn.com.eju.deal.base.dto.log.ErrorLogDto;
import cn.com.eju.deal.base.helper.LogHelper;
import cn.com.eju.deal.base.log.model.ErrorLog;
import cn.com.eju.deal.base.log.service.ErrorLogService;
import cn.com.eju.deal.core.support.ResultData;
import cn.com.eju.deal.core.support.ReturnView;
import cn.com.eju.deal.core.util.JsonUtil;

/**   
* error日志
* @author (li_xiaodong)
* @date 2016年1月19日 下午6:05:44
*/

@RestController
@RequestMapping(value = "errorlogs")
public class ErrorLogController extends BaseController
{
    
    /**
    * 日志
    */
    private final LogHelper logger = LogHelper.getLogger(this.getClass());
    
    @Resource(name = "errorLogService")
    private ErrorLogService errorLogService;
    
    /** 
     * 查询-对象
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable int id)
    {
        //构建返回
        ReturnView<String, Object> jv = new ReturnView<String, Object>();
        
        //查询
        
        return jv.toString();
    }
    
    /** 
    * 创建
    * @param studentDtoJson 对象字符串
    * @return
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(@RequestBody String jsonDto)
    {
        //构建返回
        ResultData<ErrorLogDto> resultData = new ResultData<ErrorLogDto>();
        
        ErrorLogDto errorLogDto = JsonUtil.parseToObject(jsonDto, ErrorLogDto.class);
        
        ErrorLog errorLog = new ErrorLog();
        
        //赋值
        BeanUtils.copyProperties(errorLogDto, errorLog);
        
        int count = errorLogService.create(errorLog);
        
        if (count <= 0)
        {
            resultData.setFail();
        }
        
        return resultData.toString();
        
    }
    
}
