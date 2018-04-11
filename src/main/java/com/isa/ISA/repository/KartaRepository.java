package com.isa.ISA.repository;

import com.isa.ISA.dbModel.Karta;
import com.isa.ISA.dbModel.PozoristeBioskop;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface KartaRepository extends CrudRepository<Karta, Long> {
    List<Karta> findByPozoristeBioskopAndVremeOdrzavanjaBetween(PozoristeBioskop pb, Date pocetak, Date kraj); // Jako se nadam da ce ovo raditi :D Ako ne pisem svcj sql
    List<Karta> findByPozoristeBioskopAndVremeOdrzavanjaAfter(PozoristeBioskop pb, Date pocetak); // Jako se nadam da ce ovo raditi :D Ako ne pisem svcj sql
    List<Karta> findByPozoristeBioskopAndVremeOdrzavanjaBefore(PozoristeBioskop pb, Date kraj); // Jako se nadam da ce ovo raditi :D Ako ne pisem svcj sql

}
