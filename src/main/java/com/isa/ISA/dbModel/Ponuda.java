package com.isa.ISA.dbModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.isa.ISA.dbModel.korisnici.Korisnik;

/**
 * Ponuda u licitaciji
 */
@Entity
public class Ponuda {

    @Id
    @GeneratedValue
    private long id;
    
    private String ponudio;//username korisnika koji je dao ponudu

    private double suma;
    

    @JsonBackReference
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

    public String getPonudio() {
        return ponudio;
    }

    public void setPonudio(String ponudio) {
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
