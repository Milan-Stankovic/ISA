package com.isa.ISA.dbModel.korisnici;



import com.isa.ISA.dbModel.Rekvizit;
import com.isa.ISA.dbModel.Rezervacija;

import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
@Entity
@Table(name = "registrovaniKor")
public class RegistrovaniKorisnik extends Korisnik {

    @OneToMany
    private List<Prijatelj> prijatelji;

    private int bodovi;

    @OneToMany
    private List<Poziv> pozivi;

    @OneToMany
    private List<Rekvizit> rekviziti;

    @OneToMany
    private List<Rezervacija> rezervacije;

    public RegistrovaniKorisnik(){}

    public List<Prijatelj> getPrijatelji() {
        return prijatelji;
    }

    public void setPrijatelji(List<Prijatelj> prijatelji) {
        this.prijatelji = prijatelji;
    }

    public int getBodovi() {
        return bodovi;
    }

    public void setBodovi(int bodovi) {
        this.bodovi = bodovi;
    }

    public List<Poziv> getPozivi() {
        return pozivi;
    }

    public void setPozivi(List<Poziv> pozivi) {
        this.pozivi = pozivi;
    }

    public List<Rekvizit> getRekviziti() {
        return rekviziti;
    }

    public void setRekviziti(List<Rekvizit> rekviziti) {
        this.rekviziti = rekviziti;
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }
}
