package cn.com.eju.deal.test.activeMQ;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.eju.deal.base.jms.IActivemqMsgSender;

public class MsgSenderTest
{
    public static void main(String[] args)
    {
        
        //SpringConfigHelper.getDaoBeanByDaoClassName("studentMapper");
        
        //IActivemqMsgSender sender1 =(IActivemqMsgSender)SpringConfigHelper.getBean("IActivemqMsgSender");
        
        //sender1.sendMessage("Hello");
        
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("activemq-template.xml");
        
        IActivemqMsgSender sender = ctx.getBean(IActivemqMsgSender.class);
        
        sender.sendMessage("Hello");
        
        ctx.close();
        
    }
}
