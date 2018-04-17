package com.isa.ISA.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.Ponuda;
import com.isa.ISA.dbModel.korisnici.Korisnik;


public interface PonudaRepository extends CrudRepository<Ponuda, Long>{
}
