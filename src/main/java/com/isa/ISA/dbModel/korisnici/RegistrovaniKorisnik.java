package com.isa.ISA.dbModel.korisnici;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
@Entity
@Table(name = "korisnici")
public class RegistrovaniKorisnik extends Korisnik {

    @OneToMany
    private List<Prijatelj> prijatelji;

    private int bodovi;

    @OneToMany
    private List<Poziv> pozivi;

}
