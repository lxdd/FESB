package cn.com.eju.deal.base.jms;

import org.springframework.stereotype.Component;

@Component
public interface IActivemqMsgReceiver
{
    String receiveMessage(); 
}
