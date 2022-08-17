package com.zeiss.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeiss.domain.MachineStream;
import com.zeiss.domain.PayLoad;
import com.zeiss.domain.db.MachineStreamModel;
import com.zeiss.repository.MachineStreamRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class MachineStreamServiceImpl implements MachineStreamService {

    @Autowired
    MachineStreamRepository machineStreamRepository;
    @Override
    public MachineStream saveStreamData(MachineStream machineStream) {
        MachineStreamModel model = new MachineStreamModel();
        model.setRef(machineStream.getRef());
        model.setTopic(machineStream.getTopic());
        model.setEvent(machineStream.getEvent());
        model.setPid(machineStream.getPayload().getId());
        model.setMachine_id(machineStream.getPayload().getMachine_id());
        model.setTimestamp(machineStream.getPayload().getTimestamp());
        model.setStatus(machineStream.getPayload().getStatus());
        machineStreamRepository.save(model);
        return machineStream;
    }

    @Override
    public List<MachineStream> getStreamData(MachineStream machineStram) {
        return null;
    }

    @Override
    public List<MachineStream> getStreamData() {
        List<MachineStream> streamList = new ArrayList<>();
        Iterable<MachineStreamModel> streamDataList = machineStreamRepository.findAll();
        for(MachineStreamModel machineStreamModel : streamDataList) {
            System.out.print(machineStreamModel.getPid());
            MachineStream machineStream = new MachineStream();
            machineStream.setEvent(machineStreamModel.getEvent());
            PayLoad payload = new PayLoad();
            payload.setId(machineStreamModel.getPid());
            payload.setMachine_id(machineStreamModel.getMachine_id());
            payload.setStatus(machineStreamModel.getStatus());
            payload.setMachine_id(machineStreamModel.getTimestamp());
            machineStream.setPayload(payload);
            machineStream.setRef(machineStreamModel.getRef());
            machineStream.setTopic(machineStreamModel.getTopic());
            streamList.add(machineStream);
        }
        return streamList;
    }
}
