package com.isa.ISA.repository;

import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.enums.TipUstanove;
import com.isa.ISA.dbModel.korisnici.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PozoristeBioskopRepository extends CrudRepository<PozoristeBioskop, Long> {

    List<PozoristeBioskop> findByTip(TipUstanove tip); // Mozda ce trebati rename ovako @Column(unique = true, name = "tip")
    List<PozoristeBioskop> findByNaziv(String naziv);



}
