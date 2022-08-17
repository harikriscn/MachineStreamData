package com.zeiss.jms;

import com.google.gson.Gson;
import com.zeiss.domain.MachineStream;
import com.zeiss.service.WebSocketClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class JmsReceiver {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    WebSocketClientService webSocketClientService;
    @JmsListener(destination = "messages", containerFactory = "myFactory")
    public void receiveMessage(String message) {
        Message messageObj = jmsTemplate.receive("messages");
        if(messageObj != null) {
            TextMessage textMessage = (TextMessage) messageObj;
            try {
                if(textMessage != null) {
                    String text = textMessage.getText();
                    Gson gson = new Gson();
                    MachineStream machineStream = gson.fromJson(text, MachineStream.class);
                    webSocketClientService.writeInToRepository(machineStream);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
