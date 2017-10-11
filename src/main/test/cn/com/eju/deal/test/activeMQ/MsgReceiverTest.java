package cn.com.eju.deal.test.activeMQ;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.eju.deal.base.jms.IActivemqMsgReceiver;

public class MsgReceiverTest
{
    
    public static void main(String[] args)
    {
        
        
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("activemq-template.xml");
        
        IActivemqMsgReceiver receive = ctx.getBean(IActivemqMsgReceiver.class);
        
        String receiveMsg = receive.receiveMessage();
        System.out.println(receiveMsg);
        
        ctx.close();
        
    }
    
}