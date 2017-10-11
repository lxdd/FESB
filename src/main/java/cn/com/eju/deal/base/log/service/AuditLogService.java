package cn.com.eju.deal.base.log.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.eju.deal.base.log.dao.AuditLogMapper;
import cn.com.eju.deal.base.log.model.AuditLog;
import cn.com.eju.deal.base.service.BaseService;

/**   
* ErrorLogService 服务类
* @author li_xiaodong
* @date 2016年2月2日 下午7:57:09
*/
@Service("auditLogService")
public class AuditLogService extends BaseService
{
    
    @Resource(name="auditLogMapper")
    private AuditLogMapper auditLogMapper;
    
    /** 
     * 创建
     * @param param
     * @return
     */
    public int create(AuditLog auditLog)
    {
        int count = auditLogMapper.create(auditLog);
        return count;
    }
    
}
