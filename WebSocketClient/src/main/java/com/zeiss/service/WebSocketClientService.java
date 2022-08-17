package com.zeiss.service;

import com.zeiss.domain.MachineStream;

import java.util.List;

public interface WebSocketClientService {

    public void callWebSocket();
    public void writeInToRepository(MachineStream streamData);

}
