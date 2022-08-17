package com.zeiss.service;

import com.google.gson.Gson;
import com.zeiss.domain.MachineStream;
import com.zeiss.websocket.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class WebSocketClientServiceImpl implements WebSocketClientService{

    @Autowired
    MachineStreamService machineStreamService;
    int count=0;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void callWebSocket() {
        WebSocketClient webSocketClient = new WebSocketClient();
        webSocketClient.createSessionAndConnectToserver(jmsTemplate);

    }

    @Override
    public void writeInToRepository(MachineStream streamData) {
        try {
            machineStreamService.saveStreamData(streamData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
