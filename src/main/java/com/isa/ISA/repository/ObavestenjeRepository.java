package com.isa.ISA.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.Karta;
import com.isa.ISA.dbModel.Obavestenje;
import com.isa.ISA.dbModel.PozoristeBioskop;

public interface ObavestenjeRepository  extends CrudRepository<Obavestenje, Long> {
	List<Obavestenje> findByPrimaAndDeleted(String prima, boolean deleted);
}
