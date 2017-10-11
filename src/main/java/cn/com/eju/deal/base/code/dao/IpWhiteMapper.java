package cn.com.eju.deal.base.code.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.eju.deal.base.code.model.IpWhite;
import cn.com.eju.deal.core.dao.IDao;

/**   
* IP白名单
* @author (guo_wei)
* @date 2016年1月4日 下午4:33:53
*/
@Repository(value="ipWhiteMapper")
public interface IpWhiteMapper extends IDao<IpWhite>
{
    Integer getIpCheckResult(BigInteger paramIp) throws Exception;
    
    /** 
    * 根据wsNo查询
    * @param wsNo
    * @return
    */
    List<IpWhite> getIpListByAppId(Map<String, String> reqMap);
}
