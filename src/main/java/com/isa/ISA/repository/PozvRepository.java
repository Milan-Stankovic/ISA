package com.isa.ISA.repository;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.korisnici.Poziv;

public interface PozvRepository extends CrudRepository<Poziv, Long> {
}
