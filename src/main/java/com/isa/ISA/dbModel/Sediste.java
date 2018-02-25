package com.isa.ISA.dbModel;


import com.isa.ISA.dbModel.enums.TipSedista;

import javax.persistence.*;

//GOTOVO

@Entity
public class Sediste {

    @Id
    @GeneratedValue
    private long id;

    private int red;

    private int broj;

    @Enumerated(EnumType.STRING)
    private TipSedista tipSedista;

    public Sediste(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public TipSedista getTipSedista() {
        return tipSedista;
    }

    public void setTipSedista(TipSedista tipSedista) {
        this.tipSedista = tipSedista;
    }
}
