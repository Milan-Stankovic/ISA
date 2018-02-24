package com.isa.ISA.dbModel.korisnici;

import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.enums.TipAdmina;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admini")
public class Admin extends Korisnik {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipAdmina tip;

    @ManyToMany
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
