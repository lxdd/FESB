package cn.com.eju.deal.base.jms.impl;


import org.springframework.jms.core.JmsTemplate;

import cn.com.eju.deal.base.jms.IActivemqMsgSender;

public class ActivemqMsgSenderImpl implements IActivemqMsgSender {

    private JmsTemplate jmsTemplate;
    
    public ActivemqMsgSenderImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    
    @Override
    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(message);
    }

}
