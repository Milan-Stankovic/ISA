package com.isa.ISA.dbModel.korisnici;

import com.isa.ISA.dbModel.enums.TipAdmina;

import javax.persistence.*;

@Entity
@Table(name = "admini")
public class Admin extends Korisnik {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipAdmina tip;

    public Admin(){ super(); }

    public TipAdmina getTip() {
        return tip;
    }

    public void setTip(TipAdmina tip) {
        this.tip = tip;
    }

}
