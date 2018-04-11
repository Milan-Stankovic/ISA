package com.isa.ISA.dbModel.korisnici;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.isa.ISA.dbModel.enums.StatusPrijateljstva;

@Entity
public class Prijatelj {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private RegistrovaniKorisnik posiljalac;

    @ManyToOne
    private RegistrovaniKorisnik primalac;

    @Enumerated(EnumType.STRING)
    private StatusPrijateljstva status;

    public Prijatelj(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RegistrovaniKorisnik getPosiljalac() {
        return posiljalac;
    }

    public void setPosiljalac(RegistrovaniKorisnik posiljalac) {
        this.posiljalac = posiljalac;
    }

    public RegistrovaniKorisnik getPrimalac() {
        return primalac;
    }

    public void setPrimalac(RegistrovaniKorisnik primalac) {
        this.primalac = primalac;
    }

    public StatusPrijateljstva getStatus() {
        return status;
    }

    public void setStatus(StatusPrijateljstva status) {
        this.status = status;
    }
}
