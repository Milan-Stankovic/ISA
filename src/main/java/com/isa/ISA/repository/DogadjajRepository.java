package com.isa.ISA.repository;

import com.isa.ISA.dbModel.Dogadjaj;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DogadjajRepository extends CrudRepository<Dogadjaj,Long> {
    List<Dogadjaj> findByNaziv(String naziv);

<<<<<<< HEAD
=======

>>>>>>> 41f836e809e58017fb215de2bb4d3e438cb1b333
}
