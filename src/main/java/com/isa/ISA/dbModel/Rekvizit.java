package com.isa.ISA.dbModel;

import com.isa.ISA.dbModel.enums.StatusLicitacije;
import com.isa.ISA.dbModel.korisnici.Korisnik;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Rekvizit {
    @Id
    @GeneratedValue
    private long id;

    //@Lob
    //private byte[] slika; Ovo je valjda za slike ali nisam siguran...

    @ManyToOne
    private Korisnik postavio;

    private Date kraj;

    @ManyToOne
    private PozoristeBioskop preuzeti;

    @OneToMany
    private List<Ponuda> licitacija;

    @Enumerated(EnumType.STRING)
    private StatusLicitacije status;

    public Rekvizit(){}

    public List<Ponuda> getLicitacija() {
        return licitacija;
    }

    public void setLicitacija(List<Ponuda> licitacija) {
        this.licitacija = licitacija;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Korisnik getPostavio() {
        return postavio;
    }

    public void setPostavio(Korisnik postavio) {
        this.postavio = postavio;
    }

    public Date getKraj() {
        return kraj;
    }

    public void setKraj(Date kraj) {
        this.kraj = kraj;
    }

    public PozoristeBioskop getPreuzeti() {
        return preuzeti;
    }

    public void setPreuzeti(PozoristeBioskop preuzeti) {
        this.preuzeti = preuzeti;
    }
}
