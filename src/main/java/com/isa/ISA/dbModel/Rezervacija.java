package com.isa.ISA.dbModel;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;

//GOTOVO

@Entity
public class Rezervacija {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany
    @JsonBackReference
    private List<Poziv> urezervaciji;

    @ManyToOne
    @JsonBackReference
  //  @JoinColumn(nullable = false) Da bi omogucio one promotivne karte
    private RegistrovaniKorisnik rezervisao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Projekcija projekcija;

    private int popust; // Da bi se omogucili dodatni popusti i one promo karte

    public Rezervacija(){}

    public Projekcija getProjekcija() {
        return projekcija;
    }

    public void setProjekcija(Projekcija projekcija) {
        this.projekcija = projekcija;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Poziv> getUrezervaciji() {
        return urezervaciji;
    }

    public void setUrezervaciji(List<Poziv> urezervaciji) {
        this.urezervaciji = urezervaciji;
    }

    public RegistrovaniKorisnik getRezervisao() {
        return rezervisao;
    }

    public void setRezervisao(RegistrovaniKorisnik rezervisao) {
        this.rezervisao = rezervisao;
    }
}
