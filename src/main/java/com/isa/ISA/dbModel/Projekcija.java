package com.isa.ISA.dbModel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Projekcija {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Dogadjaj dogadjaj;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Sala sala;

    /**
     * Ovde cuvamo sva zauzeta sedista, ta sedista moraju biti ista ona koja su u Sali, tj. tokom rezervacije se mora dodati to mesto u ovu listu.
     */
    @ManyToMany
    private List<Sediste> zauzetaSedista;

    @OneToMany
    private List<Rezervacija> rezervacije;

    @Column(nullable = false)

    private Date vreme;

    @Column(nullable = false)
    private double cena;

    public Projekcija(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Dogadjaj getDogadjaj() {
        return dogadjaj;
    }

    public void setDogadjaj(Dogadjaj dogadjaj) {
        this.dogadjaj = dogadjaj;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<Sediste> getZauzetaSedista() {
        return zauzetaSedista;
    }

    public void setZauzetaSedista(List<Sediste> zauzetaSedista) {
        this.zauzetaSedista = zauzetaSedista;
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }
}
