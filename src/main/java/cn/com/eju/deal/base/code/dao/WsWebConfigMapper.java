package cn.com.eju.deal.base.code.dao;

import org.springframework.stereotype.Repository;

import cn.com.eju.deal.base.code.model.WsWebConfig;
import cn.com.eju.deal.core.dao.IDao;

/**   
* WS配置
* @author (li_xiaodong)
* @date 2015年12月25日 下午4:22:47
*/
@Repository(value="wsWebConfigMapper")
public interface WsWebConfigMapper extends IDao<WsWebConfig> 
{
    
    /** 
    * 根据wsKey获取配置
    * @param wsKey
    * @return
    */
    WsWebConfig getByWSKey(String wsKey) throws Exception;
}