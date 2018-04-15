package com.isa.ISA.DTO;

import com.isa.ISA.dbModel.Sediste;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;

import java.util.List;

public class RezervacijaDTO {
    private List<Long> sedista;
    private Long rezervisao;
    private List<Long> pozvani;
    private Long projekcija;
    private int popust;

    public RezervacijaDTO() { }

    public RezervacijaDTO(List<Long> sedista, Long rezervisao, List<Long> pozvani, Long projekcija, int popust) {
        this.sedista = sedista;
        this.rezervisao = rezervisao;
        this.pozvani = pozvani;
        this.projekcija = projekcija;
        this.popust = popust;
    }

    public List<Long> getSedista() {
        return sedista;
    }

    public void setSedista(List<Long> sedista) {
        this.sedista = sedista;
    }

    public Long getRezervisao() {
        return rezervisao;
    }

    public void setRezervisao(Long rezervisao) {
        this.rezervisao = rezervisao;
    }

    public List<Long> getPozvani() {
        return pozvani;
    }

    public void setPozvani(List<Long> pozvani) {
        this.pozvani = pozvani;
    }

    public Long getProjekcija() {
        return projekcija;
    }

    public void setProjekcija(Long projekcija) {
        this.projekcija = projekcija;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }
}
