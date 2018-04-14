package com.isa.ISA.DTO;

import java.util.List;

public class SalaDTO {

    private int brRed;

    private int brSedista;

    private List<SedisteDTO> sedista;

    private String ime;

    private Long ustanova;

    public SalaDTO(){

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

    public List<SedisteDTO> getSedista() {
        return sedista;
    }

    public void setSedista(List<SedisteDTO> sedista) {
        this.sedista = sedista;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Long getUstanova() {
        return ustanova;
    }

    public void setUstanova(Long ustanova) {
        this.ustanova = ustanova;
    }
}
