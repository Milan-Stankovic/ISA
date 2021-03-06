package com.isa.ISA.dbModel;


import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import com.isa.ISA.dbModel.enums.TipUstanove;
import com.isa.ISA.dbModel.korisnici.Admin;

@Entity
public class PozoristeBioskop {
    @Id
    @GeneratedValue
    private long id;

    private String naziv;

    private String opis;

    private String adresa;

    @Length(max=2084)
    private String urlMape;

    @Enumerated(EnumType.STRING)
    private TipUstanove tip;

    @OneToMany
    @JsonBackReference
    private List<Sala> sale;

    @OneToMany
    @JsonBackReference
    private List<Projekcija> projekcije;

    @ManyToMany
    @JsonBackReference
    private List<Admin> admini;

    private float prosecnaOcena;

    private int brojOcena; // Postaviti na 0 na pocetku

    public PozoristeBioskop(){}

    public int getBrojOcena() {
        return brojOcena;
    }

    public void setBrojOcena(int brojOcena) {
        this.brojOcena = brojOcena;
    }

    public String getUrlMape() {
        return urlMape;
    }

    public void setUrlMape(String urlMape) {
        this.urlMape = urlMape;
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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public TipUstanove getTip() {
        return tip;
    }

    public void setTip(TipUstanove tip) {
        this.tip = tip;
    }

    public List<Sala> getSale() {
        return sale;
    }

    public void setSale(List<Sala> sale) {
        this.sale = sale;
    }

    public List<Projekcija> getProjekcije() {
        return projekcije;
    }

    public void setProjekcije(List<Projekcija> projekcije) {
        this.projekcije = projekcije;
    }

    public List<Admin> getAdmini() {
        return admini;
    }

    public void setAdmini(List<Admin> admini) {
        this.admini = admini;
    }
}
