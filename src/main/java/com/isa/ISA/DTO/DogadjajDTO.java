package com.isa.ISA.DTO;

import com.isa.ISA.dbModel.enums.Zanr;



public class DogadjajDTO {


    private String naziv;

    private int trajanje;

    private Zanr zanr;

    private String opis;

    private String reziser;

    private String glumciStr;

    private int donsiBodova;

    private Long pbId;

    private String slika;

    public Long getPbId() {
        return pbId;
    }

    public void setPbId(Long pbId) {
        this.pbId = pbId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Zanr getZanr() {
        return zanr;
    }

    public void setZanr(Zanr zanr) {
        this.zanr = zanr;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getReziser() {
        return reziser;
    }

    public void setReziser(String reziser) {
        this.reziser = reziser;
    }

    public String getGlumciStr() {
        return glumciStr;
    }

    public void setGlumciStr(String glumciStr) {
        this.glumciStr = glumciStr;
    }

    public DogadjajDTO(){}

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public int getDonsiBodova() {
        return donsiBodova;
    }

    public void setDonsiBodova(int donsiBodova) {
        this.donsiBodova = donsiBodova;
    }
}
