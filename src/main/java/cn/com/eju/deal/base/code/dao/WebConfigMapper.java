package cn.com.eju.deal.base.code.dao;

import org.springframework.stereotype.Repository;

import cn.com.eju.deal.base.code.model.WebConfig;
import cn.com.eju.deal.core.dao.IDao;

/**   
* TODO (这里用一句话描述这个文件的作用)
* @author (li_xiaodong)
* @date 2016年1月4日 下午4:34:28
*/
@Repository(value="webConfigMapper")
public interface WebConfigMapper extends IDao<WebConfig>
{
    public WebConfig getByName(String webConfigName) throws Exception;
}