package com.isa.ISA.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.ZvanicanRekvizit;

public interface RekvizitRepository extends CrudRepository<ZvanicanRekvizit, Long> {
	List<ZvanicanRekvizit> findByAktivan(boolean aktivan);
}
