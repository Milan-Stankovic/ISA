package com.isa.ISA.dbModel.korisnici;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.isa.ISA.dbModel.PolovanRekvizit;
import com.isa.ISA.dbModel.Ponuda;
import com.isa.ISA.dbModel.Rezervacija;
@Entity
public class RegistrovaniKorisnik extends Korisnik {

	@JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    private List<Prijatelj> prijatelji;

    private int bodovi;

    @OneToMany
    private List<Poziv> pozivi;

    @OneToMany
    private List<PolovanRekvizit> rekviziti;

    @ManyToMany
    private List<Rezervacija> rezervacije;

    @OneToMany
    private List<Ponuda> ponude;

    public RegistrovaniKorisnik(){}

    public List<Ponuda> getPonude() {
        return ponude;
    }

    public void setPonude(List<Ponuda> ponude) {
        this.ponude = ponude;
    }

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

    public List<PolovanRekvizit> getRekviziti() {
        return rekviziti;
    }

    public void setRekviziti(List<PolovanRekvizit> rekviziti) {
        this.rekviziti = rekviziti;
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }
}
