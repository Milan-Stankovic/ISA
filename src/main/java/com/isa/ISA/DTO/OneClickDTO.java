package com.isa.ISA.DTO;

import java.util.List;

public class OneClickDTO {

    private List<SedisteDTO> sedista;

    private int brojSedista;

    private int brojRedova;

    private int cena;

    private Long bpId;

    public OneClickDTO() {
    }

    public Long getBpId() {
        return bpId;
    }

    public void setBpId(Long bpId) {
        this.bpId = bpId;
    }

    public List<SedisteDTO> getSedista() {
        return sedista;
    }

    public void setSedista(List<SedisteDTO> sedista) {
        this.sedista = sedista;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getBrojSedista() {
        return brojSedista;
    }

    public void setBrojSedista(int brojSedista) {
        this.brojSedista = brojSedista;
    }

    public int getBrojRedova() {
        return brojRedova;
    }

    public void setBrojRedova(int brojRedova) {
        this.brojRedova = brojRedova;
    }
}
