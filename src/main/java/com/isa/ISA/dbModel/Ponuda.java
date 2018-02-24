package com.isa.ISA.dbModel;

import com.isa.ISA.dbModel.korisnici.Korisnik;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
    private Rekvizit rekvizit;

    private boolean prihvaceno;

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

    public Rekvizit getRekvizit() {
        return rekvizit;
    }

    public void setRekvizit(Rekvizit rekvizit) {
        this.rekvizit = rekvizit;
    }

    public boolean isPrihvaceno() {
        return prihvaceno;
    }

    public void setPrihvaceno(boolean prihvaceno) {
        this.prihvaceno = prihvaceno;
    }
}
