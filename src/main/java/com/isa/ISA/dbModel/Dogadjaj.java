package com.isa.ISA.dbModel;

import com.isa.ISA.dbModel.enums.Zanr;

import javax.persistence.*;
import java.util.List;


/**
 * Ova klasa predstavlja Film ili Predstavu
 */
@Entity
public class Dogadjaj {

    @Id
    @GeneratedValue
    private long id;

    //@Lob
    //private byte[] slika; Ovo je valjda za slike ali nisam siguran...

    private String naziv;

    private int trajanje;

    @Enumerated(EnumType.STRING)
    private Zanr zanr;

    private String opis;

    private String reziser;

    @ManyToMany
    private List<Glumac> glumci; // Ne dozvoljava cist String iz nekog razloga

    public Dogadjaj(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Zanr getZanr() {
        return zanr;
    }

    public void setZanr(Zanr zanr) {
        this.zanr = zanr;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getReziser() {
        return reziser;
    }

    public void setReziser(String reziser) {
        this.reziser = reziser;
    }

    public List<Glumac> getGlumci() {
        return glumci;
    }

    public void setGlumci(List<Glumac> glumci) {
        this.glumci = glumci;
    }
}
