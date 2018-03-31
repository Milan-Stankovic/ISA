package com.isa.ISA.repository;

import com.isa.ISA.dbModel.korisnici.Poziv;
import org.springframework.data.repository.CrudRepository;

public interface PozvRepository extends CrudRepository<Poziv, Long> {
}
