package com.isa.ISA.repository;

import com.isa.ISA.dbModel.Karta;
import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PozivRepository extends CrudRepository<Poziv, Long> {
    List<Poziv> findByRezervacija(Rezervacija id);
    List<Poziv> findByOsoba(RegistrovaniKorisnik id);
    List<Poziv> findByOsobaAndPozvan(RegistrovaniKorisnik id, boolean b);
    List<Poziv> findByKarta(Karta id);

}
