package com.isa.ISA.dbModel;

import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;

import javax.persistence.*;
import java.util.List;

@Entity
public class Rezervacija {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany
    private List<Poziv> urezervaciji;

    @ManyToOne
    @JoinColumn(nullable = false)
    private RegistrovaniKorisnik rezervisao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Projekcija projekcija;

    public Rezervacija(){}

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
