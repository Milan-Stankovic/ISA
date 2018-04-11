package com.isa.ISA.dbModel;

import com.isa.ISA.dbModel.enums.TipSedista;

import javax.persistence.*;

@Entity
public class CenovnikSedista {

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private TipSedista tip;

    @ManyToOne
    private PozoristeBioskop ustanova;

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

    public PozoristeBioskop getUstanova() {
        return ustanova;
    }

    public void setUstanova(PozoristeBioskop ustanova) {
        this.ustanova = ustanova;
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
