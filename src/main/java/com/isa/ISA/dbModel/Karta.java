package com.isa.ISA.dbModel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Karta {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Sediste sediste;

    @ManyToOne
    private  PozoristeBioskop pozoristeBioskop;

    private Date vremeOdrzavanja;

    public Karta(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Sediste getSediste() {
        return sediste;
    }

    public void setSediste(Sediste sediste) {
        this.sediste = sediste;
    }

    public PozoristeBioskop getPozoristeBioskop() {
        return pozoristeBioskop;
    }

    public void setPozoristeBioskop(PozoristeBioskop pozoristeBioskop) {
        this.pozoristeBioskop = pozoristeBioskop;
    }

    public Date getVremeOdrzavanja() {
        return vremeOdrzavanja;
    }

    public void setVremeOdrzavanja(Date vremeOdrzavanja) {
        this.vremeOdrzavanja = vremeOdrzavanja;
    }

    public int getPunaCena() {
        return punaCena;
    }

    public void setPunaCena(int punaCena) {
        this.punaCena = punaCena;
    }

    private int punaCena;


}
