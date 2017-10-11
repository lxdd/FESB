package cn.com.eju.deal.base.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**   
* 日志打印
* @author (li_xiaodong)
* @date 2015年11月12日 上午9:41:05
*/
@Component
public class AppInitServlet extends HttpServlet
{
    
    private static final long serialVersionUID = 1L;
    
    protected Logger logger = Logger.getLogger(AppInitServlet.class);
    
    @Override
    public void init()
        throws ServletException
    {
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Init AppInitializeServlet");
        }
        
        String rootPath = this.getServletContext().getRealPath("/");
        
        logger.info(rootPath);
        
        String log4jPath = this.getServletConfig().getInitParameter("oss.log4j.path");
        //若没有指定oss.log4j.path初始参数，则使用WEB的工程目录  
        log4jPath = (log4jPath == null || "".equals(log4jPath)) ? rootPath : log4jPath;
        System.setProperty("oss.log4j.path", log4jPath);
        
        super.init();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        doPost(req, resp);
        
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        
        super.doPost(req, resp);
    }
    
}
