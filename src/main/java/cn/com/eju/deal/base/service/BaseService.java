package cn.com.eju.deal.base.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import cn.com.eju.deal.base.helper.LogHelper;
import cn.com.eju.deal.base.model.PageInfo;
import cn.com.eju.deal.core.support.QueryConst;
import cn.com.eju.deal.core.support.ResultData;
import cn.com.eju.deal.core.support.SystemCfg;
import cn.com.eju.deal.core.util.JsonUtil;
import cn.com.eju.deal.core.util.StringUtil;

/**   
* 调用REST服务类的基类-专供文件渠道系统使用
* @author (li_xiaodong)
* @date 2016年2月16日 下午3:10:06
* @param <T>
*/
public abstract class BaseService<T>
{
    
    /**
    * 日志
    */
    private final LogHelper logger = LogHelper.getLogger(this.getClass());
    
    /**
    * Spring提供的用于访问Rest服务的客户端
    */
    private RestTemplate restTemplate = new RestTemplate();
    
    private final static String FILE_REST_SERVICE = SystemCfg.getString("filesRestServer");
    
    /**
     * 发起Get请求
     *
     * @param url 请求的地址
     * @return 返回请求的内容
     */
    public String getForFY(String url)
        throws IOException
    {
        
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        
        return EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
    }
    
    /** 
    * GET,   不带参数
    * @param url
    * @param id
    * @return
    * @throws Exception
    */
    public ResultData<?> get(String url)
        throws Exception
    {
        Object obj = null;
        return get(url, obj);
    }
    
    /** 
    * GET,  带参数，参数类型Object... urlVariables
    * @param url
    * @param id
    * @return
    * @throws Exception
    */
    public ResultData<?> get(String url, Object... urlVariables)
        throws Exception
    {
        //日志记录begin
        long startTime = System.currentTimeMillis();
        logger.info("BaseService request url=" + url + "; request params: urlVariables=" + urlVariables);
        
        //HTTP GET
        ResultData<?> backResult = new ResultData<T>();
        
        try
        {
            //HTTP GET
            backResult = restTemplate.getForObject(url, ResultData.class, urlVariables);
            
        }
        catch (Exception e)
        {
            //logger.error(moduleName, className, methodName, dto.toString(), operateId, ipAddress, description, e);
            throw e;
        }
        
        //日志记录end
        long endTime = System.currentTimeMillis();
        logger.info("BaseService response url=" + url + "; response data:" + JsonUtil.parseToJson(backResult) + "; cost time:("
            + (endTime - startTime) + ") ms");
        
        return backResult;
    }
    
    /** 
     *  GET,  默认不分页、带参数，参数类型（Map<String, ?>）
     * @param url
     * @param reqMap
     * @return
     * @throws Exception
     */
    public ResultData<?> get(String url, Map<String, Object> reqMap)
        throws Exception
    {
        return get(url, reqMap, null);
        
    }
    
    /** 
    * GET,   分页、带参数，参数类型（Map<String, ?>）
    * @param url
    * @param reqMap
    * @return
    * @throws Exception
    */
    public ResultData<?> get(String url, Map<String, Object> reqMap, PageInfo pageInfo)
        throws Exception
    {
        if (null != pageInfo)
        {
            reqMap.put(QueryConst.PAGE_IDX, pageInfo.getCurPage().toString());
            reqMap.put(QueryConst.PAGE_SIZE, pageInfo.getPageLimit().toString());
        }
        
        //日志记录begin
        long startTime = System.currentTimeMillis();
        
        //记录日志step2
        logger.info("BaseService request url=" + url + "; request params:" + JsonUtil.parseToJson(reqMap));
        
        //HTTP GET
        ResultData<?> reback = restTemplate.getForObject(url, ResultData.class, JsonUtil.parseToJson(reqMap));
        
        //日志记录end
        long endTime = System.currentTimeMillis();
        
        //记录日志step3
        logger.info("BaseService response url=" + url + "; response data:" + JsonUtil.parseToJson(reback)
            + "; cost time:(" + (endTime - startTime) + ") ms");
        
        if (null != pageInfo && null != reback)
        {
            
            //记录总数
            String total = (String)reback.getTotalCount();
            
            if (StringUtil.isNotEmpty(total))
            {
                
                pageInfo.setDataCount(Integer.valueOf(total));
            }
        }
        
        return reback;
        
    }
    
    /** 
    * post 请求 -- 默认鉴权
    * @param url
    * @param dto
    * @return
    * @throws Exception
    */
    public ResultData<?> post(String url, T dto)
        throws Exception
    {
        return post(url, dto, true);
    }
    
    /** 
    * post 请求 -- 鉴权
    * @param url
    * @param dto
    * @return
    * @throws Exception
    */
    public ResultData<?> post(String url, T dto, boolean isUserAuth)
        throws Exception
    {
        
        //日志记录begin
        long startTime = System.currentTimeMillis();
        logger.info("BaseService request url=" + url + "; request params:" + dto.toString());
        
        HttpHeaders headers = new HttpHeaders();
        
        //需要鉴权
        if (isUserAuth)
        {
            Map<String, Object> authMap = new HashMap<String, Object>();
            
            try
            {
                headers.add("authInfo", URLEncoder.encode(JsonUtil.parseToJson(authMap), "UTF-8"));
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            
        }
        
        HttpEntity<T> formEntity = new HttpEntity<T>(dto, headers);
        
        ResultData<?> backResult = new ResultData<T>();
        
        try
        {
            //HTTP POST
            backResult = restTemplate.postForObject(url, formEntity, ResultData.class);
        }
        catch (Exception e)
        {
            //logger.error(moduleName, className, methodName, dto.toString(), operateId, ipAddress, description, e);
            throw e;
        }
        
        //日志记录end
        long endTime = System.currentTimeMillis();
        logger.info("BaseService response url=" + url + "; response data:" + JsonUtil.parseToJson(backResult) + "; cost time:("
            + (endTime - startTime) + ") ms");
        
        return backResult;
    }
    
    /** 
     * post 请求 -- 鉴权
     * @param url
     * @param dto
     * @return
     * @throws Exception
     */
    public ResultData<?> post(String url, String jsonDto)
        throws Exception
    {
        
        //日志记录begin
        long startTime = System.currentTimeMillis();
        
        //记录日志step2
        logger.info("BaseService request url=" + url + "; request params jsonDto=:" + jsonDto.toString());
        
        HttpHeaders headers = new HttpHeaders();
        
        //需要鉴权
        if (true)
        {
            
            //应用授权Code
            headers.add("appCode", "FESB");
            
            //签名时间(默认 单位秒)
            long timestamp = System.currentTimeMillis() / 1000;
            headers.add("timestamp", timestamp + "");
            
        }
        
        T dto = (T)JsonUtil.parseToObject(jsonDto, Object.class);
        
        HttpEntity<T> formEntity = new HttpEntity<T>(dto, headers);
        
        ResultData<?> backResult = new ResultData<T>();
        
        try
        {
            //HTTP POST
            backResult = restTemplate.postForObject(url, formEntity, ResultData.class);
        }
        catch (Exception e)
        {
            //logger.error(moduleName, className, methodName, dto.toString(), operateId, ipAddress, description, e);
            throw e;
        }
        
        //日志记录end
        long endTime = System.currentTimeMillis();
        
        //记录日志step3
        logger.info("BaseService response url=" + url + "; response data:" + JsonUtil.parseToJson(backResult)
            + "; cost time:(" + (endTime - startTime) + ") ms");
        
        return backResult;
    }
    
    /** 
    * 修改
    * @param url
    * @param dto
    * @throws Exception
    */
    public void put(String url, T dto)
        throws Exception
    {
        //默认需要鉴权
        put(url, dto, true);
    }
    
    /** 
    * 修改--鉴权
    * @param url
    * @param dto
    * @param isUserAuth 是否需要鉴权
    * @throws Exception
    */
    public void put(String url, T dto, boolean isUserAuth)
        throws Exception
    {
        //日志记录begin
        long startTime = System.currentTimeMillis();
        logger.info("BaseService request url=" + url + "; request params:" + dto.toString());
        
        HttpHeaders headers = new HttpHeaders();
        
        //需要鉴权
        if (isUserAuth)
        {
            Map<String, Object> authMap = new HashMap<String, Object>();
            
            try
            {
                headers.add("authInfo", URLEncoder.encode(JsonUtil.parseToJson(authMap), "UTF-8"));
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            
        }
        
        HttpEntity<T> formEntity = new HttpEntity<T>(dto, headers);
        
        try
        {
            //HTTP PUT
            restTemplate.put(url, formEntity, dto);
        }
        catch (Exception e)
        {
            //logger.error(moduleName, className, methodName, dto.toString(), operateId, ipAddress, description, e);
            throw e;
        }
        
        //日志记录end
        long endTime = System.currentTimeMillis();
        logger.info("BaseService response url=" + url + "; response data:" + "HTTP PUT 请求" + "; cost time:("
            + (endTime - startTime) + ") ms");
        
    }
    
    /** 
    * 删除
    * @param url
    * @param id
    * @param updateId
    * @throws Exception
    */
    public void delete(String url, Object id)
        throws Exception
    {
        //日志记录begin
        long startTime = System.currentTimeMillis();
        logger.info("BaseService request url=" + url + "; request params: id=" + id);
        
        try
        {
            //HTTP DELETE
            restTemplate.delete(url, id);
        }
        catch (Exception e)
        {
            //logger.error(moduleName, className, methodName, dto.toString(), operateId, ipAddress, description, e);
            throw e;
        }
        
        //日志记录end
        long endTime = System.currentTimeMillis();
        logger.info("BaseService response url=" + url + "; response data:" + "HTTP DELETE 请求" + "; cost time:("
            + (endTime - startTime) + ") ms");
        
    }
    
    /** 
    * 删除
    * @param url
    * @param id
    * @param updateId
    * @throws Exception
    */
    public void delete(String url, Object id, int updateId)
        throws Exception
    {
        //日志记录begin
        long startTime = System.currentTimeMillis();
        logger.info("BaseService request url=" + url + "; request params: id=" + id + ",updateId=" + updateId);
        
        try
        {
            //HTTP DELETE
            restTemplate.delete(url, id, updateId);
        }
        catch (Exception e)
        {
            //logger.error(moduleName, className, methodName, dto.toString(), operateId, ipAddress, description, e);
            throw e;
        }
        
        //日志记录end
        long endTime = System.currentTimeMillis();
        logger.info("BaseService response url=" + url + "; response data:" + "HTTP DELETE 请求" + "; cost time:("
            + (endTime - startTime) + ") ms");
        
    }
    
    /** 
    * 获取文件渠道系统配置（外部文件系统的配置）
    * @return
    * @throws Exception
    */
    public Map<?, ?> getChannelConfig()
        throws Exception
    {
        //获取渠道配置信息
        Map<?, ?> mapTemp = null;
        try
        {
            
            //调用 接口
            String url = FILE_REST_SERVICE + "/config";
            
            ResultData<?> backResult = get(url);
            
            //页面数据
            mapTemp = (Map<?, ?>)backResult.getReturnData();
            
        }
        catch (Exception e)
        {
            logger.error("BaseService", "BaseService", "getChannelConfig", "", -1, "", "获取渠道配置异常", e);
            
        }
        
        return mapTemp;
    }
}
