package com.isa.ISA.repository;

import java.util.List;

import com.isa.ISA.dbModel.korisnici.Korisnik;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Rezervacija;

public interface RezervacijaRepository extends CrudRepository<Rezervacija,Long> {
    List<Rezervacija> findByProjekcija(Projekcija id);
    List<Rezervacija> findByRezervisaoUserName(String id);
    List<Rezervacija> findByRezervisaoAndProjekcijaSalaUstanovaId(RegistrovaniKorisnik rk, Long id);
    List<Rezervacija> findByRezervisao(RegistrovaniKorisnik rk);
    List<Rezervacija> findByRezervisaoAndProjekcijaAktivnaAndProjekcijaSalaUstanovaId(RegistrovaniKorisnik rk, boolean b, Long id);
}
