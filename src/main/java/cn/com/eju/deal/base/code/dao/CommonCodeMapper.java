package cn.com.eju.deal.base.code.dao;

import org.springframework.stereotype.Repository;

import cn.com.eju.deal.base.code.model.Code;
import cn.com.eju.deal.core.dao.IDao;

/**   
* TODO (这里用一句话描述这个文件的作用)
* @author (li_xiaodong)
* @date 2016年2月17日 下午9:39:21
*/
@Repository(value="commonCodeMapper")
public interface CommonCodeMapper extends IDao<Code> {

}