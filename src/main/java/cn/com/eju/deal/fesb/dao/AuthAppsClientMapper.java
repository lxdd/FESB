package cn.com.eju.deal.fesb.dao;

import org.apache.ibatis.annotations.Param;

import cn.com.eju.deal.core.dao.IDao;
import cn.com.eju.deal.fesb.model.AuthAppsClient;

/** 
* FESB认证路由 DAO层
* @author lxd 
* @date 2016年9月5日 下午2:53:33 
*  
*/
public interface AuthAppsClientMapper extends IDao<Object>
{
    
    /** 
    * 获取  应用端 apps 认证信息 
    * @return     
    */
    AuthAppsClient getAuthInfo(@Param("appKey")String appKey, @Param("resourceCode")String resourceCode);
}