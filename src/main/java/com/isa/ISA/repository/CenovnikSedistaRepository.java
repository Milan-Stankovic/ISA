package com.isa.ISA.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.CenovnikSedista;
import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.dbModel.enums.TipSedista;

public interface CenovnikSedistaRepository extends CrudRepository<CenovnikSedista, Long> {

    List<CenovnikSedista> findBySalaId(Long id);
    List<CenovnikSedista> findBySala(Sala s);

}
