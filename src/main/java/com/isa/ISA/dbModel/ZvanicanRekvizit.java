package com.isa.ISA.dbModel;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.isa.ISA.dbModel.korisnici.Admin;

@Entity
public class ZvanicanRekvizit {
	@Id
    @GeneratedValue
    private long id;
    
    private String naziv;
    
    private String opis;
    
    @ManyToOne
    private Admin postavio;
    
    @ManyToOne
    private PozoristeBioskop preuzeti;
    
    private double cena;
    
    private boolean aktivan;

    @OneToMany
    private List<RezervacijaRekvizita> rezervacije;

	

	public ZvanicanRekvizit() {
	}

	public long getId() {
		return id;
	}

	public String getNaziv() {
		return naziv;
	}

	public String getOpis() {
		return opis;
	}

	public Admin getPostavio() {
		return postavio;
	}

	public PozoristeBioskop getPreuzeti() {
		return preuzeti;
	}

	public double getCena() {
		return cena;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public void setPostavio(Admin postavio) {
		this.postavio = postavio;
	}

	public void setPreuzeti(PozoristeBioskop preuzeti) {
		this.preuzeti = preuzeti;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
	
	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	public List<RezervacijaRekvizita> getRezervacije() {
		return rezervacije;
	}

	public void setRezervacije(List<RezervacijaRekvizita> rezervacije) {
		this.rezervacije = rezervacije;
	}
    
}
