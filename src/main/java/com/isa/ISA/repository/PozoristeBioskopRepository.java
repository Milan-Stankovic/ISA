package com.isa.ISA.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.enums.TipUstanove;

public interface PozoristeBioskopRepository extends CrudRepository<PozoristeBioskop, Long> {

    List<PozoristeBioskop> findByTip(TipUstanove tip); // Mozda ce trebati rename ovako @Column(unique = true, name = "tip")
    List<PozoristeBioskop> findByNaziv(String naziv);



}
