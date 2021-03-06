package com.isa.ISA.dbModel;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;

import static org.hibernate.annotations.CascadeType.DELETE_ORPHAN;

@Entity
public class Sala {

    @Id
    @GeneratedValue
    private long id;

    private int brRed;
    /**
     * Broj sedista po redu
     */
    private int brSedista;

    /**
     * Lista svih sedisat u sali
     */

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE })
    private List<Sediste> sedista;

    private String ime;

    @ManyToOne
    @JsonBackReference
    private PozoristeBioskop ustanova;

    public Sala(){}

    public PozoristeBioskop getUstanova() {
        return ustanova;
    }

    public void setUstanova(PozoristeBioskop ustanova) {
        this.ustanova = ustanova;
    }

    public List<Sediste> getSedista() {
        return sedista;
    }

    public void setSedista(List<Sediste> sedista) {
        this.sedista = sedista;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBrRed() {
        return brRed;
    }

    public void setBrRed(int brRed) {
        this.brRed = brRed;
    }

    public int getBrSedista() {
        return brSedista;
    }

    public void setBrSedista(int brSedista) {
        this.brSedista = brSedista;
    }

    public String getIme() { return ime; }

    public void setIme(String ime) { this.ime = ime; }
}
