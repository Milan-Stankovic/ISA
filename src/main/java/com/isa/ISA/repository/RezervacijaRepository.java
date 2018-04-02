package com.isa.ISA.repository;

import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Rezervacija;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RezervacijaRepository extends CrudRepository<Rezervacija,Long> {
    List<Rezervacija> findByRezervisao(Rezervacija id);
    List<Rezervacija> findByProjekcija(Projekcija id);

}
