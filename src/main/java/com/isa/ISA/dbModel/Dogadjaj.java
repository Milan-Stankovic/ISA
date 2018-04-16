package com.isa.ISA.dbModel;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.isa.ISA.dbModel.enums.Zanr;


/**
 * Ova klasa predstavlja Film ili Predstavu
 */
@Entity
public class Dogadjaj {

    @Id
    @GeneratedValue
    private long id;

    private String slika; //Ovo je valjda za slike ali nisam siguran...

    private String naziv;

    private int trajanje;

    @Enumerated(EnumType.STRING)
    private Zanr zanr;

    private String opis;

    private String reziser;

    private float prosecnaOcena;

    private int brojOcena; //Postaviti na nula kada se kreira

    private int donosiBodova;

    private String glumciStr;

    @ManyToOne
    @JsonBackReference
    private PozoristeBioskop mestoOdrzavanja;

    //@ManyToMany
   // private List<Glumac> glumci; Deprecated - Mrzi me da pravim fontend za dodavanje jos jedne liste

    public Dogadjaj(){}

    public int getBrojOcena() {
        return brojOcena;
    }

    public void setBrojOcena(int brojOcena) {
        this.brojOcena = brojOcena;
    }

    public int getDonosiBodova() {
        return donosiBodova;
    }

    public void setDonosiBodova(int donosiBodova) {
        this.donosiBodova = donosiBodova;
    }

    public float getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(float prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

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

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public PozoristeBioskop getMestoOdrzavanja() {
        return mestoOdrzavanja;
    }

    public void setMestoOdrzavanja(PozoristeBioskop mestoOdrzavanja) {
        this.mestoOdrzavanja = mestoOdrzavanja;
    }

    public String getGlumciStr() {
        return glumciStr;
    }

    public void setGlumciStr(String glumciStr) {
        this.glumciStr = glumciStr;
    }
}
