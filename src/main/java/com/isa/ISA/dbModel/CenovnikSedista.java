package com.isa.ISA.dbModel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.isa.ISA.dbModel.enums.TipSedista;

@Entity
public class CenovnikSedista {

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private TipSedista tip;


    @ManyToOne
    private Sala sala;

    private int doplata;

    public CenovnikSedista(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipSedista getTip() {
        return tip;
    }

    public void setTip(TipSedista tip) {
        this.tip = tip;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public int getDoplata() {
        return doplata;
    }

    public void setDoplata(int doplata) {
        this.doplata = doplata;
    }
}
