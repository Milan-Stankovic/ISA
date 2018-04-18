package com.isa.ISA.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Rezervacija;

public interface RezervacijaRepository extends CrudRepository<Rezervacija,Long> {
    List<Rezervacija> findByRezervisao(Rezervacija id);
    List<Rezervacija> findByProjekcija(Projekcija id);
    List<Rezervacija> findByRezervisaoUserName(String id);
}
