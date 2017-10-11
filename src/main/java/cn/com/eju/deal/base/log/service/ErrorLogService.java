package cn.com.eju.deal.base.log.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.eju.deal.base.log.dao.ErrorLogMapper;
import cn.com.eju.deal.base.log.model.ErrorLog;
import cn.com.eju.deal.base.service.BaseService;

/**   
* ErrorLogService 服务类
* @author li_xiaodong
* @date 2016年2月2日 下午7:57:09
*/
@Service("errorLogService")
public class ErrorLogService extends BaseService
{
    
    @Resource(name="errorLogMapper")
    private ErrorLogMapper errorLogMapper;
    
    /** 
     * 创建
     * @param param
     * @return
     */
    public int create(ErrorLog errorLog)
    {
        int count = errorLogMapper.create(errorLog);
        return count;
    }
    
}
