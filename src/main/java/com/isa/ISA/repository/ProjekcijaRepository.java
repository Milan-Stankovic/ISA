package com.isa.ISA.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.Dogadjaj;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Sala;


public interface ProjekcijaRepository extends CrudRepository<Projekcija, Long> {

    List<Projekcija> findByDogadjaj(Dogadjaj d);
    List<Projekcija> findByDogadjajAndAktivna(Dogadjaj d, boolean b);
    List<Projekcija> findBySala(Sala s);
    List<Projekcija> findBySalaAndAktivna(Sala s, boolean b);
    List<Projekcija> findBySalaAndDogadjaj(Sala s, Dogadjaj d);
    List<Projekcija> findBySalaAndDogadjajAndAktivna(Sala s, Dogadjaj d, boolean b);
    List<Projekcija> findBySalaAndVremeAfter(Sala s, Date d);
    List<Projekcija> findBySalaAndVremeAfterAndAktivna(Sala s, Date d, boolean b);
    List<Projekcija> findBySalaAndVremeBefore(Sala s, Date d);
    List<Projekcija> findBySalaAndVremeBeforeAndAktivna(Sala s, Date d, boolean b);
    List<Projekcija> findBySalaAndVremeBetween(Sala s, Date d, Date b);
    List<Projekcija> findBySalaAndVremeBetweenAndAktivna(Sala s, Date d, Date b, boolean b1);
    List<Projekcija> findByVremeBetween(Date pocetak, Date kraj);
    List<Projekcija> findByVremeBetweenAndAktivna(Date pocetak, Date kraj, boolean b);
    List<Projekcija> findByVremeAfter(Date kraj);
    List<Projekcija> findByVremeAfterAndAktivna(Date kraj, boolean b);
    List<Projekcija> findByVremeBefore(Date pocetak);
    List<Projekcija> findByVremeBeforeAndAktivna(Date pocetak, boolean b);
    Projekcija getProjekcijaById(Long id);

}
