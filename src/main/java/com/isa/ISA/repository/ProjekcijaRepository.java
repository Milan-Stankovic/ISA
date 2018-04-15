package com.isa.ISA.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.Dogadjaj;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Sala;


public interface ProjekcijaRepository extends CrudRepository<Projekcija, Long> {

    List<Projekcija> findByDogadjaj(Dogadjaj d);
    List<Projekcija> findBySala(Sala s);
    List<Projekcija> findBySalaAndDogadjaj(Sala s, Dogadjaj d);
    List<Projekcija> findBySalaAndVremeAfter(Sala s, Date d);
    List<Projekcija> findBySalaAndVremeBefore(Sala s, Date d);
    List<Projekcija> findBySalaAndVremeBetween(Sala s, Date d, Date b);
    List<Projekcija> findByVremeBetween(Date pocetak, Date kraj);
    List<Projekcija> findByVremeAfter(Date kraj);
    List<Projekcija> findByVremeBefore(Date pocetak);
    Projekcija getProjekcijaById(Long id);

}
