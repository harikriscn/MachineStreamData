package com.zeiss.repository;

import com.zeiss.domain.db.MachineStreamModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineStreamRepository extends CrudRepository<MachineStreamModel, Long> {
}
