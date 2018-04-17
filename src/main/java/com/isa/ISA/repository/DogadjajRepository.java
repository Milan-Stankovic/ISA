package com.isa.ISA.repository;

import java.util.List;

import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.enums.DogadjajStatus;
import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.Dogadjaj;

public interface DogadjajRepository extends CrudRepository<Dogadjaj,Long> {
    List<Dogadjaj> findByNaziv(String naziv);
    List<Dogadjaj> findByNazivAndStatus(String naziv, DogadjajStatus d);
    List<Dogadjaj> findByMestoOdrzavanjaAndStatus(PozoristeBioskop pb, DogadjajStatus d);
    List<Dogadjaj> findByMestoOdrzavanja(PozoristeBioskop pb);
}
