package com.isa.ISA.dbModel;

import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;

import javax.persistence.*;
import java.util.List;

//GOTOVO

@Entity
public class Rezervacija {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany
    private List<Poziv> urezervaciji;

    @ManyToOne
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
