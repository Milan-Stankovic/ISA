package com.isa.ISA.dbModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.isa.ISA.dbModel.korisnici.Korisnik;

/**
 * Ponuda u licitaciji
 */
@Entity
public class Ponuda {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Korisnik ponudio;

    private double suma;

    @ManyToOne
    private PolovanRekvizit rekvizit;

    private Boolean prihvaceno;

    public Ponuda(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Korisnik getPonudio() {
        return ponudio;
    }

    public void setPonudio(Korisnik ponudio) {
        this.ponudio = ponudio;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public PolovanRekvizit getRekvizit() {
        return rekvizit;
    }

    public void setRekvizit(PolovanRekvizit rekvizit) {
        this.rekvizit = rekvizit;
    }

    public Boolean isPrihvaceno() {
        return prihvaceno;
    }

    public void setPrihvaceno(Boolean prihvaceno) {
        this.prihvaceno = prihvaceno;
    }
}
