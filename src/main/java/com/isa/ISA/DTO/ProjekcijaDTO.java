package com.isa.ISA.DTO;

public class ProjekcijaDTO {

    private int cena;

    private Long sala;

    private Long dogadjaj;

    private Long ustanova;

    private String date;

    public ProjekcijaDTO(){}

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Long getSala() {
        return sala;
    }

    public void setSala(Long sala) {
        this.sala = sala;
    }

    public Long getDogadjaj() {
        return dogadjaj;
    }

    public void setDogadjaj(Long dogadjaj) {
        this.dogadjaj = dogadjaj;
    }

    public Long getUstanova() {
        return ustanova;
    }

    public void setUstanova(Long ustanova) {
        this.ustanova = ustanova;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
