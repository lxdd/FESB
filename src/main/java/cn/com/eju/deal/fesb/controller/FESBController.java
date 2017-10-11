package cn.com.eju.deal.fesb.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.eju.deal.base.controller.BaseController;
import cn.com.eju.deal.base.helper.LogHelper;
import cn.com.eju.deal.common.util.FesbReturnCode;
import cn.com.eju.deal.core.support.ResultData;
import cn.com.eju.deal.core.support.ReturnCode;
import cn.com.eju.deal.core.util.AuthUtil;
import cn.com.eju.deal.core.util.JsonUtil;
import cn.com.eju.deal.core.util.StringUtil;
import cn.com.eju.deal.fesb.model.AuthAppsClient;
import cn.com.eju.deal.fesb.service.AuthService;
import cn.com.eju.deal.fesb.service.FESBService;

/**
 * 
 * HTTP GET POST 认证、路由  服务
 * @author (li_xiaodong)
 * @date 2016年1月19日 下午6:05:44
 */

@RestController
@RequestMapping(value = "route")
public class FESBController extends BaseController
{
    
    /**
     * 日志
     */
    private final LogHelper logger = LogHelper.getLogger(this.getClass());
    
    @Resource(name = "fESBService")
    private FESBService fESBService;
    
    @Resource(name = "authService")
    private AuthService authService;
    
    /**
     * HTTP GET 认证、路由
     * 一、认证：1、check 入参非空；  2、校验时间戳是否过期；  3、校验Ip白名单 ； 4、校验APPKey；5、校验签名sign。
     * 二、路由；
     * @param param 查询条件
     * @return
     */
    @RequestMapping(value = "/{param}", method = RequestMethod.GET)
    public String httpGet(@PathVariable String param, HttpServletRequest request)
    {
        //构建返回
        ResultData<?> resultData = new ResultData<>();
        
        @SuppressWarnings("unchecked")
        Map<String, Object> reqMap = JsonUtil.parseToObject(param, Map.class);
        
        //HTTP GET 认证
        ResultData<?> resultCheck;
        try
        {
            resultCheck = checkHttpParams(request, reqMap);
        }
        catch (Exception e)
        {
            
            logger.error("FESB", "FESBController", "httpGet", "input params:" + param, null, null, "FESB HTTP GET 认证失败", e);
            
            resultData.setReturnCode(FesbReturnCode.AUTH_HTTP_GET_ERROR);
            resultData.setReturnMsg("HTTP GET 认证失败");
            return resultData.toString();
        }
        if (!ReturnCode.SUCCESS.equals(resultCheck.getReturnCode()))
        {
            logger.error("FESB",
                "FESBController",
                "httpGet",
                "input params:" + param,
                null,
                null,
                "FESB HTTP GET 认证失败,失败原因：" + resultCheck.getReturnMsg(),
                null);
            
            resultData.setReturnCode(resultCheck.getReturnCode());
            resultData.setReturnMsg(resultCheck.getReturnMsg());
            return resultData.toString();
        }
        
        //HTTP GET 路由
        String url = (String)resultCheck.getReturnData();
        
        try
        {
            resultData = fESBService.httpGet(reqMap, url);
        }
        catch (Exception e)
        {
            
            logger.error("FESB", "FESBController", "httpGet", "input params: url = " + url + "; param" + param, null, null, "FESB HTTP GET 路由失败", e);
            
            resultData.setReturnCode(FesbReturnCode.ROUTE_HTTP_GET_ERROR);
            resultData.setReturnMsg("HTTP GET 路由失败");
            return resultData.toString();
        }
        
        return resultData.toString();
    }
    
    /**
     * 
     * HTTP POST 认证、路由
     * 一、认证：1、check 入参非空；  2、校验时间戳是否过期；  3、校验Ip白名单 ； 4、校验APPKey；5、校验签名sign。
     * 二、路由；
     * @param jsonDto 对象字符串
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String httpPost(@RequestBody String jsonDto, HttpServletRequest request)
    {
        
        //构建返回
        ResultData<?> resultData = new ResultData<>();
        
        //HTTP POST 认证
        ResultData<?> resultCheck;
        try
        {
            resultCheck = checkHttpParams(request, jsonDto);
        }
        catch (Exception e1)
        {
            logger.error("FESB", "FESBController", "httpPost", "input params:" + jsonDto, null, null, "FESB HTTP POST 认证失败", e1);
            
            resultData.setReturnCode(FesbReturnCode.AUTH_HTTP_POST_ERROR);
            resultData.setReturnMsg("HTTP POST 认证失败");
            return resultData.toString();
        }
        
        if (!ReturnCode.SUCCESS.equals(resultCheck.getReturnCode()))
        {
            logger.error("FESB",
                "FESBController",
                "httpPost",
                "input params:" + jsonDto,
                null,
                null,
                "FESB HTTP POST 认证失败,失败原因：" + resultCheck.getReturnMsg(),
                null);
            
            resultData.setReturnCode(resultCheck.getReturnCode());
            resultData.setReturnMsg(resultCheck.getReturnMsg());
            return resultData.toString();
        }
        
        //HTTP POST 路由
        String url = (String)resultCheck.getReturnData();
        
        try
        {
            
            resultData = fESBService.httpPost(url, jsonDto);
            
        }
        catch (Exception e)
        {
            logger.error("FESB",
                "FESBController",
                "httpPost",
                "input params: url = " + url + "; jsonDto = " + jsonDto,
                null,
                null,
                "FESB HTTP POST 路由失败",
                e);
            
            resultData.setReturnCode(FesbReturnCode.ROUTE_HTTP_POST_ERROR);
            resultData.setReturnMsg("HTTP POST 路由失败");
            return resultData.toString();
        }
        
        return resultData.toString();
    }
    
    /** 
     *  HTTP 认证
     *  一：入参判空（timestamp、appKey、sign）；
     *  二： 白名单验证
     *  三：参数完整性sign验证
     * @param reqMap
     * @throws Exception 
     * 
     */
    private ResultData<?> checkHttpParams(HttpServletRequest request, Map<String, Object> reqMap)
        throws Exception
    {
        return checkHttpParams(reqMap, request, null);
    }
    
    /** 
     *  HTTP 认证
     *  一：入参判空（timestamp、appKey、sign）；
     *  二： 白名单验证
     *  三：参数完整性sign验证
     * @param request
     * @param jsonDto
     * @throws Exception 
     * 
     */
    private ResultData<?> checkHttpParams(HttpServletRequest request, String jsonDto)
        throws Exception
    {
        return checkHttpParams(null, request, jsonDto);
    }
    
    /** 
     *  HTTP 认证
     *  一：入参判空（timestamp、appKey、sign）；
     *  二： 白名单验证
     *  三：参数完整性sign验证
     * @param reqMap
     * @param httpType (httpGet、httpPost)
     * @param request
     * @throws Exception 
     * 
     */
    private ResultData<?> checkHttpParams(Map<String, Object> reqMap, HttpServletRequest request, String jsonDto)
        throws Exception
    {
        //返回resultData
        ResultData<String> resultData = new ResultData<>();
        
        String timestamp = request.getHeader("timestamp");
        String appKey = request.getHeader("appkey");
        String signed = request.getHeader("sign");
        //String appCode = request.getHeader("appcode");
        String method = request.getHeader("method");
        String version = request.getHeader("apiversion");
        
        //待加密jsonstr
        String jsonStr = null;
        
        String httpMethod = null;
        String params = null;
        
        if (null != reqMap)
        {
            httpMethod = "GET";
            params = reqMap.toString();
            
            jsonStr = JsonUtil.parseToJson(reqMap);
        }
        else
        {
            
            httpMethod = "POST";
            params = jsonDto;
            
            jsonStr = jsonDto;
        }
        
        //返回resultMsg
        StringBuilder sb = new StringBuilder();
        sb.append("[request header{appkey=" + appKey + ";method=" + method + ";signed=" + signed + ";timestamp=" + timestamp + ";apiversion="
            + version + "};request params=" + params + ";request httpMethod=" + httpMethod + ";");
        
        //记录日志step1
        logger.info(sb.append("]").toString());
        
        //check null timestamp
        
        if (StringUtil.isEmpty(timestamp))
        {
            //记录日志step1
            logger.error(sb.append(";error msg:{timestamp不能为空}]").toString());
            
            resultData.setReturnCode(FesbReturnCode.PARAM_NULL_TIMESTAMP);
            resultData.setReturnMsg(sb.toString());
            return resultData;
        }
        
        //check null 应用访问授权appKey
        
        if (StringUtil.isEmpty(appKey))
        {
            //记录日志step1
            logger.error(sb.append(";error msg:{appKey不能为空}]").toString());
            resultData.setReturnCode(FesbReturnCode.PARAM_NULL_APPKEY);
            resultData.setReturnMsg(sb.toString());
            return resultData;
        }
        
        //check null 应用sign
        
        if (StringUtil.isEmpty(signed))
        {
            //记录日志step1
            logger.error(sb.append(";error msg:{sign不能为空}]").toString());
            resultData.setReturnCode(FesbReturnCode.PARAM_NULL_SIGN);
            resultData.setReturnMsg(sb.toString());
            return resultData;
        }
        
        //获取  apps 认证信息 
        AuthAppsClient authAppsClient = authService.getAppsAuthInfo(appKey, method);
        
        if (null == authAppsClient)
        {
            //记录日志step1
            logger.error(sb.append(";error msg:{请求资源不存在}]").toString());
            resultData.setReturnCode(FesbReturnCode.RESOURCE_NO_EXIST);
            resultData.setReturnMsg(sb.toString());
            return resultData;
        }
        
        //二： 白名单验证
        boolean isBWAuth = authAppsClient.getIpbwFlag();
        if (isBWAuth)
        {
            //TODO 
        }
        
        //三：参数完整性sign验证
        //查出appSecret,根据APPKey.
        String appSecret = authAppsClient.getAppSecret();
        
        String sign = AuthUtil.sign(appSecret, jsonStr);
        
        if (!sign.equals(signed))
        {
            //记录日志step1
            logger.error(sb.append(";error msg:{sign参数签名不一致}]").toString());
            resultData.setReturnCode(FesbReturnCode.PARAM_NO_SAME_SIGN);
            resultData.setReturnMsg(sb.toString());
            return resultData;
        }
        
        //设置  路由 地址
        String resourceUrl = authAppsClient.getSystemAddr() + authAppsClient.getResourceUrl();
        
        resultData.setReturnData(resourceUrl);
        
        return resultData;
        
    }
    
}
