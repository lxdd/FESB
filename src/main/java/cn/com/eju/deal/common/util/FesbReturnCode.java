package cn.com.eju.deal.common.util;

public interface FesbReturnCode
{
    /**
     * 必填参数timestamp不能为空  41501
     */
    String PARAM_NULL_TIMESTAMP = "41501";
    
    /**
     * 必填参数appKey不能为空  41502
     */
    String PARAM_NULL_APPKEY = "41502";
    
    /**
     * 必填参数sign不能为空  41503
     */
    String PARAM_NULL_SIGN = "41503";
    
    /**
     * sign参数签名不一致 41504
     */
    String PARAM_NO_SAME_SIGN = "41504";
    
    /**
     * HTTP GET 认证失败  41505
     */
    String AUTH_HTTP_GET_ERROR = "41505";
    
    /**
     * HTTP POST 认证失败  41506
     */
    String AUTH_HTTP_POST_ERROR = "41506";
    
    /**
     * HTTP GET 路由失败  41507
     */
    String ROUTE_HTTP_GET_ERROR = "41507";
    
    /**
     * HTTP POST 路由失败  41508
     */
    String ROUTE_HTTP_POST_ERROR = "41508";
    
    /**
     * 请求资源不存在  41509
     */
    String RESOURCE_NO_EXIST = "41509";
}
