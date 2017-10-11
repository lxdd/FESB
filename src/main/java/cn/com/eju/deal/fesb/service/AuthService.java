package cn.com.eju.deal.fesb.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.eju.deal.base.service.BaseService;
import cn.com.eju.deal.fesb.dao.AuthAppsClientMapper;
import cn.com.eju.deal.fesb.model.AuthAppsClient;

/**   
* service层
* @author li_xiaodong
* @date 2016年2月2日 下午7:57:09
*/
@Service("authService")
public class AuthService extends BaseService<Object>
{
    
    @Resource
    private AuthAppsClientMapper authAppsClientMapper;
    
    /** 
    * 获取  apps 认证信息 
    * @param appCode
    * @param appKey
    * @param resourceCode
    * @return authAppsClient
    * @throws Exception
    */
    public AuthAppsClient getAppsAuthInfo(String appKey, String resourceCode)
        throws Exception
    {
        AuthAppsClient authAppsClient = authAppsClientMapper.getAuthInfo(appKey, resourceCode);
        
        return authAppsClient;
    }
    
    
}
