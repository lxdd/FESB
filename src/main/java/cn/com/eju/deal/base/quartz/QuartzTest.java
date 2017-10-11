package cn.com.eju.deal.base.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.com.eju.deal.base.helper.LogHelper;
  
  
public class QuartzTest extends QuartzJobBean{  
      
    /**
    * 日志
    */
    private final LogHelper logger = LogHelper.getLogger(this.getClass());
          
    public void executeAction(){  
        logger.info("Hello quartz");  
    }  
  
    @Override  
    protected void executeInternal(JobExecutionContext arg0)  
            throws JobExecutionException {  
          
    }  
  
}  

