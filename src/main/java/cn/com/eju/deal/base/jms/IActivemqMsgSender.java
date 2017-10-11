package cn.com.eju.deal.base.jms;

import org.springframework.stereotype.Component;

@Component
public interface IActivemqMsgSender
{
    void sendMessage(String message); 
}
