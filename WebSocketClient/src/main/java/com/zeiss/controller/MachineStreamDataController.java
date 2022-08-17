package com.zeiss.controller;

import com.zeiss.domain.MachineStream;
import com.zeiss.service.MachineStreamService;
import com.zeiss.service.WebSocketClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MachineStreamDataController {

    @Autowired
    WebSocketClientService webSocketClientService;

    @Autowired
    MachineStreamService machineStreamService;

    @GetMapping(value = "/callWebsocket")
    public void callWebsocket() {
        webSocketClientService.callWebSocket();
    }

    @GetMapping(value = "/getMachineData")
    public List<MachineStream> getMachineData() {
        return machineStreamService.getStreamData();

    }
}
