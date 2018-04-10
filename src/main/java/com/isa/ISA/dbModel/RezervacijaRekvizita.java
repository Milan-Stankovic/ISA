package com.isa.ISA.dbModel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.isa.ISA.dbModel.korisnici.Korisnik;

@Entity
public class RezervacijaRekvizita {
	@Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Korisnik izvrsio;

    private Date datum;
    
    private boolean reseno;

	public RezervacijaRekvizita() {
	}

	public long getId() {
		return id;
	}

	public Korisnik getIzvrsio() {
		return izvrsio;
	}

	public Date getDatum() {
		return datum;
	}

	public boolean isReseno() {
		return reseno;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIzvrsio(Korisnik izvrsio) {
		this.izvrsio = izvrsio;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public void setReseno(boolean reseno) {
		this.reseno = reseno;
	}
    
    
    
}
