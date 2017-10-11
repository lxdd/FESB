package cn.com.eju.deal.fesb.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.com.eju.deal.base.service.BaseService;
import cn.com.eju.deal.core.support.ResultData;

/**   
* Service层
* @author (li_xiaodong)
* @date 2016年2月2日 下午9:30:27
*/
@Service("fESBService")
public class FESBService extends BaseService<Object>
{
    
    /** 
    * HTTP GET 路由  查询-list
    * @param reqMap
    * @return
    * @throws Exception
    */
    public ResultData<?> httpGet(Map<String, Object> reqMap, String url)
        throws Exception
    {
        
        //调用 接口
        ResultData<?> reback = get(url, reqMap);
        
        return reback;
        
    }
    
    /** 
    * HTTP POST 路由    创建
    * @param studentDto
    * @return
    * @throws Exception
    */
    public ResultData<?> httpPost(String url, String jsonDto)
        throws Exception
    {
        
        ResultData<?> backResult = post(url, jsonDto);
        
        return backResult;
    }
    
    
}
