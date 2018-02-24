package com.isa.ISA.dbModel.korisnici;

import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.Sediste;
import com.isa.ISA.dbModel.enums.Status;


import javax.persistence.*;

@Entity
public class Poziv {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private RegistrovaniKorisnik osoba;

    private int ocenaFilma;

    private int ocenaAmbijenta;

    private boolean pozvan;

    @ManyToOne
    private Rezervacija rezervacija;

    @ManyToOne

    private Sediste sediste;

    public Poziv(){}

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public Sediste getSediste() {
        return sediste;
    }

    public void setSediste(Sediste sediste) {
        this.sediste = sediste;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public RegistrovaniKorisnik getOsoba() {
        return osoba;
    }

    public void setOsoba(RegistrovaniKorisnik osoba) {
        this.osoba = osoba;
    }

    public int getOcenaFilma() {
        return ocenaFilma;
    }

    public void setOcenaFilma(int ocenaFilma) {
        this.ocenaFilma = ocenaFilma;
    }

    public int getOcenaAmbijenta() {
        return ocenaAmbijenta;
    }

    public void setOcenaAmbijenta(int ocenaAmbijenta) {
        this.ocenaAmbijenta = ocenaAmbijenta;
    }

    public boolean isPozvan() {
        return pozvan;
    }

    public void setPozvan(boolean pozvan) {
        this.pozvan = pozvan;
    }
}
