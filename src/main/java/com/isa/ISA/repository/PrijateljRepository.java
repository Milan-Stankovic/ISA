package com.isa.ISA.repository;

import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.korisnici.Prijatelj;

public interface PrijateljRepository extends CrudRepository<Prijatelj, Long>{


}
