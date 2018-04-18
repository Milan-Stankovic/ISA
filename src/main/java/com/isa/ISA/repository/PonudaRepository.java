package com.isa.ISA.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.Ponuda;


public interface PonudaRepository extends CrudRepository<Ponuda, Long>{
	Ponuda findByPonudioAndRekvizitId(String ponudio, long id);
}
