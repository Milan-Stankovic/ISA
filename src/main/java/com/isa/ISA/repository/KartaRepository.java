package com.isa.ISA.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.Karta;
import com.isa.ISA.dbModel.PozoristeBioskop;

public interface KartaRepository extends CrudRepository<Karta, Long> {
    List<Karta> findByPozoristeBioskopAndVremeOdrzavanjaBetween(PozoristeBioskop pb, Date pocetak, Date kraj); // Jako se nadam da ce ovo raditi :D Ako ne pisem svcj sql
    List<Karta> findByPozoristeBioskopAndVremeOdrzavanjaAfter(PozoristeBioskop pb, Date pocetak); // Jako se nadam da ce ovo raditi :D Ako ne pisem svcj sql
    List<Karta> findByPozoristeBioskopAndVremeOdrzavanjaBefore(PozoristeBioskop pb, Date kraj); // Jako se nadam da ce ovo raditi :D Ako ne pisem svcj sql

}
