package com.isa.ISA.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.PolovanRekvizit;
import com.isa.ISA.dbModel.enums.StatusLicitacije;

public interface PolovanRekvRepository extends CrudRepository<PolovanRekvizit, Long> {
	List<PolovanRekvizit> findByStatus(StatusLicitacije status);
}
