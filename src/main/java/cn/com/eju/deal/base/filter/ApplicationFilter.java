package cn.com.eju.deal.base.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;

import cn.com.eju.deal.base.code.model.IpWhite;
import cn.com.eju.deal.base.helper.LogHelper;
import cn.com.eju.deal.base.support.SystemParam;
import cn.com.eju.deal.core.support.ReturnCode;
import cn.com.eju.deal.core.support.SystemCfg;
import cn.com.eju.deal.core.util.JsonUtil;
import cn.com.eju.deal.core.util.StringUtil;
import cn.com.eju.deal.core.util.WebUtil;

/**   
* (过滤器，拦截所有request请求)
* @author (li_xiaodong)
* @date 2015年10月13日 下午12:00:54
*/
public class ApplicationFilter implements Filter
{
    //过滤url
    private List<String> excludePathList;
    
    //过滤后缀
    private static String staticFileSuffix;
    
    /**
    * 日志
    */
    private static LogHelper logger = LogHelper.getLogger(ApplicationFilter.class);
    
    @Override
    public void init(FilterConfig config)
        throws ServletException
    {
        
        logger.debug("Init ApplicationFilter");
        String url = config.getInitParameter("session_exclude_url");
        if (url != null)
        {
            excludePathList = Arrays.asList(url.split(","));
            logger.info("excludePathList=" + excludePathList);
        }
        
        config.getServletContext().setAttribute("ctx", SystemCfg.getString("ctx"));
        
        config.getServletContext().setAttribute("sysConfig", SystemCfg.getAllConfig());
        
        staticFileSuffix = SystemCfg.getString("staticFileSuffix");
        logger.info("init staticFileSuffix:" + staticFileSuffix);
        
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc)
        throws IOException, ServletException
    {
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpServletRequest request = (HttpServletRequest)req;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String requestUrl = request.getRequestURI();
        requestUrl = requestUrl.substring(requestUrl.indexOf(request.getContextPath()) + request.getContextPath().length());
        String suffix = requestUrl.substring(requestUrl.lastIndexOf(".") + 1);
        
        //取header
        String appKey = request.getHeader("appkey");
        //String appCode = request.getHeader("appcode");
        String method = request.getHeader("method");
        String signed = request.getHeader("sign");
        String timestamp = request.getHeader("timestamp");
        String version = request.getHeader("apiversion");
        
        //记录请求信息
        logger.info("ApplicationFilter[requestUrl=" + requestUrl + "; request header{appkey=" + appKey + ";method=" + method + ";signed=" + signed
            + ";timestamp=" + timestamp + ";apiversion=" + version + "}]");
        
        //验证不允许过去，跳到登陆页面 (1:userInfo==null && 2、未被排除 && 3、未被资源后缀排除  && 4、非ws)
        MDC.put("ip", WebUtil.getRealIpAddress(request));
        
        try
        {
            fc.doFilter(request, resp);
        }
        catch (IOException e)
        {
            
            logger.error("", e.getMessage(), e, null);
            
            throw e;
        }
        catch (ServletException e)
        {
            logger.error("", e.getMessage(), e, null);
            throw e;
        }
        finally
        {
            MDC.remove("ip");
        }
    }
    
    /** 
    * (当filter处理不能继续进入controller时错误处理)
    * @param response
    * @param request
    * @param errorCode
    * @param redirectUrl 重定向的url(如果是ajax请求则返回状态码)
    * @throws IOException
    */
    public void errorDeal(HttpServletResponse response, HttpServletRequest request, String errorCode, String redirectUrl)
        throws IOException
    {
        String ajaxHeader = request.getHeader("X-Requested-With");
        boolean isAjaxReq = ajaxHeader != null && ajaxHeader.equals("XMLHttpRequest") ? true : false;
        if (isAjaxReq)
        {
            logger.info("ajax request userInfo is null");
            response.setHeader("status", errorCode);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        else
        {
            response.sendRedirect(SystemCfg.getString("ctx") + redirectUrl);
        }
    }
    
    @Override
    public void destroy()
    {
        logger.debug("Destroy ApplicationFilter");
    }
    
    public Map<String, Object> bindParamToMap(HttpServletRequest request)
    {
        Enumeration<?> enumer = request.getParameterNames();
        Map<String, Object> map = new HashMap<String, Object>();
        while (enumer.hasMoreElements())
        {
            String key = (String)enumer.nextElement();
            String val = request.getParameter(key);
            if (!"randomId".equals(key))
            {
                if ("orderBy".equals(key))
                {
                    if (!StringUtil.isEmpty(val))
                    {
                        Object orderByList = JsonUtil.parseToObject(val, List.class);
                        map.put(key, orderByList);
                    }
                    continue;
                }
                map.put(key, val);
            }
        }
        return map;
    }
    
    public Boolean ipCheck(HttpServletRequest request)
    {
        //获取ip白名单开关的值
        String checkflg = SystemParam.getWebConfigValue("ipWhiteCheckFlg");
        //0：不做校验  1：做校验
        if ("1".equals(checkflg))
        {
            //获取ip
            String paramIp = WebUtil.getRealIpAddress(request);
            //ip校验
            Integer rt = this.getIpCheckResult(paramIp);
            //返回值大于0，表示能找到允许的ip段
            return rt > 0;
        }
        return true;
    }
    
    public void errorIp(HttpServletResponse response)
        throws IOException
    {
        response.setHeader("status", ReturnCode.FORBIDDEN);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        //response.sendRedirect(SystemCfg.getString("ctx") + "/BlackIp.jsp");
    }
    
    public Integer getIpCheckResult(String paramIp)
    {
        //定义返回值
        int returnCount = 0;
        //参数为空
        if (StringUtil.isEmpty(paramIp))
        {
            return returnCount;
        }
        //localhost 访问
        if ("0:0:0:0:0:0:0:1".equals(paramIp) || "127.0.0.1".equals(paramIp))
        {
            returnCount = 1;
            return returnCount;
        }
        //走缓存
        //ip String => Long  转成数字型比较
        Long ipLong = ipToLong(paramIp);
        //获取缓存中的白名单list
        List<IpWhite> ipWhiteList = SystemParam.getIpWhiteList();
        //数据库没有值的情况，默认为所有ip都可以访问
        if (ipWhiteList == null || ipWhiteList.size() == 0)
        {
            returnCount = 1;
        }
        else
        {
            for (IpWhite o : ipWhiteList)
            {
                if (ipLong.compareTo(o.getIpStartInt()) >= 0 //ip 大于等于开始ip
                    && ipLong.compareTo(o.getIpEndInt()) <= 0) //ip 小于等于结算ip
                {
                    returnCount = 1;
                    break;
                }
            }
        }
        return returnCount;
    }
    
    public static long ipToLong(String strIp)
    {
        long[] ip = new long[4];
        // 先找到IP地址字符串中.的位置  
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型  
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }
    
}
