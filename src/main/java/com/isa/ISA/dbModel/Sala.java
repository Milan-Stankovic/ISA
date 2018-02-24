package com.isa.ISA.dbModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    public Sala(){};

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
}
