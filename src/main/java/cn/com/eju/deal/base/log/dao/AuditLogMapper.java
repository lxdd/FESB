package cn.com.eju.deal.base.log.dao;

import org.springframework.stereotype.Repository;

import cn.com.eju.deal.base.log.model.AuditLog;
import cn.com.eju.deal.core.dao.IDao;

/**   
*  <p>审计日志类  DAO</p>
* @author (li_xiaodong)
* @date 2015年10月23日 下午9:57:55
*/
@Repository(value="auditLogMapper")
public interface AuditLogMapper extends IDao<AuditLog>
{

}