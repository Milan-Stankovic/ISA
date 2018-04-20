package com.isa.ISA.dbModel.korisnici;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.isa.ISA.dbModel.Karta;
import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.enums.Status;

@Entity
public class Poziv {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status; // U slucaju da odbije poziv, iz tabele karti se karta

    @ManyToOne
    private RegistrovaniKorisnik osoba;

    private int ocenaFilma;

    private int ocenaAmbijenta;

    private boolean pozvan;

    private boolean ocenjeno =false;

    @ManyToOne
    private Rezervacija rezervacija;

    @ManyToOne
    private Karta karta;

    public Poziv(){}

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }


    public long getId() {
        return id;
    }

    public Karta getKarta() {
        return karta;
    }

    public void setKarta(Karta karta) {
        this.karta = karta;
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

    public boolean isOcenjeno() {
        return ocenjeno;
    }

    public void setOcenjeno(boolean ocenjeno) {
        this.ocenjeno = ocenjeno;
    }
}
