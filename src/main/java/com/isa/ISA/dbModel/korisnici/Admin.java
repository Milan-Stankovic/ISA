package com.isa.ISA.dbModel.korisnici;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.enums.TipAdmina;

@Entity
public class Admin extends Korisnik {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipAdmina tip;

    @ManyToMany
    @JsonBackReference
    private List<PozoristeBioskop> mesta;

    public Admin(){ super(); }

    public List<PozoristeBioskop> getMesta() {
        return mesta;
    }

    public void setMesta(List<PozoristeBioskop> mesta) {
        this.mesta = mesta;
    }

    public TipAdmina getTip() {
        return tip;
    }

    public void setTip(TipAdmina tip) {
        this.tip = tip;
    }

}
