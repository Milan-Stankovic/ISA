package com.isa.ISA.DTO;

public class CenovnikDTO {

    private int doplataRegular=0;

    private int doplataVIP=-1;

    private int doplataLoveBox=-1;

    private int doplataBalcony=-1;

    public CenovnikDTO() {
    }

    public int getDoplataRegular() {
        return doplataRegular;
    }

    public void setDoplataRegular(int doplataRegular) {
        this.doplataRegular = doplataRegular;
    }

    public int getDoplataVIP() {
        return doplataVIP;
    }

    public void setDoplataVIP(int doplataVIP) {
        this.doplataVIP = doplataVIP;
    }

    public int getDoplataLoveBox() {
        return doplataLoveBox;
    }

    public void setDoplataLoveBox(int doplataLoveBox) {
        this.doplataLoveBox = doplataLoveBox;
    }

    public int getDoplataBalcony() {
        return doplataBalcony;
    }

    public void setDoplataBalcony(int doplataBalcony) {
        this.doplataBalcony = doplataBalcony;
    }
}
