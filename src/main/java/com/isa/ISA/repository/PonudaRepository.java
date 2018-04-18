package com.isa.ISA.repository;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.Ponuda;


public interface PonudaRepository extends CrudRepository<Ponuda, Long>{
	Ponuda findByPonudio(String ponudio);
}
