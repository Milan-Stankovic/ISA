package com.isa.ISA.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.Dogadjaj;

public interface DogadjajRepository extends CrudRepository<Dogadjaj,Long> {
    List<Dogadjaj> findByNaziv(String naziv);
}
