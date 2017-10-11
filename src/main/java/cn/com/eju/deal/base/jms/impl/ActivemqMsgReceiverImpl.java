package cn.com.eju.deal.base.jms.impl;

import org.springframework.jms.core.JmsTemplate;

import cn.com.eju.deal.base.jms.IActivemqMsgReceiver;

public class ActivemqMsgReceiverImpl implements IActivemqMsgReceiver
{
    
    private JmsTemplate jmsTemplate;
    
    public ActivemqMsgReceiverImpl(JmsTemplate jmsTemplate)
    {
        this.jmsTemplate = jmsTemplate;
    }
    
    @Override
    public String receiveMessage()
    {
        String result = (String)jmsTemplate.receiveAndConvert();
        return result;
    }
    
}
