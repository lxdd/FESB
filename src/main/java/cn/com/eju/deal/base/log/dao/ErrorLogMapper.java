package cn.com.eju.deal.base.log.dao;

import org.springframework.stereotype.Repository;

import cn.com.eju.deal.base.log.model.ErrorLog;
import cn.com.eju.deal.core.dao.IDao;

/**   
* <p>错误日志类  DAO</p>
* @author (li_xiaodong)
* @date 2016年2月17日 下午9:40:40
*/
@Repository(value="errorLogMapper")
public interface  ErrorLogMapper extends IDao<ErrorLog>
{
   
}
