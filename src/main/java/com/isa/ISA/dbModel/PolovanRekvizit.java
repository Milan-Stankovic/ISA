package com.isa.ISA.dbModel;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.isa.ISA.dbModel.enums.StatusLicitacije;
import com.isa.ISA.dbModel.korisnici.Korisnik;

@Entity
public class PolovanRekvizit {
    @Id
    @GeneratedValue
    private long id;
    
    private String naziv;
    
    private String opis;

    private String slika;

    @ManyToOne
    private Korisnik postavio;

    private Date kraj;
    

    public String getSlika() {
		return slika;
	}


	public void setSlika(String slika) {
		this.slika = slika;
	}

	@OneToMany
    private List<Ponuda> licitacija;

    @Enumerated(EnumType.STRING)
    private StatusLicitacije status;

    public PolovanRekvizit(){}

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

	public String getNaziv() {
		return naziv;
	}

	public String getOpis() {
		return opis;
	}

	public StatusLicitacije getStatus() {
		return status;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public void setStatus(StatusLicitacije status) {
		this.status = status;
	}

    
}
