package com.zeiss.service;

import com.zeiss.domain.MachineStream;

import java.util.List;

public interface MachineStreamService {
    // Save operation
    MachineStream saveStreamData(MachineStream machineStram);

    //getOperation
    List<MachineStream> getStreamData(MachineStream machineStram);

    List<MachineStream> getStreamData();
}
