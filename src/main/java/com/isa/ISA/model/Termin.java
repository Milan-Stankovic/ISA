package com.isa.ISA.model;


import java.sql.Date;
import java.util.UUID;

public class Termin {
	private UUID id;
	private Date vremePocetka;
	private Date vremeKraja;
	private double cena;
	
	private int version;

	private Termin(){
		
	}
	
	public UUID getId() {
		return id;
	}

	public Date getVremePocetka() {
		return vremePocetka;
	}

	public Date getVremeKraja() {
		return vremeKraja;
	}

	public double getCena() {
		return cena;
	}

	public int getVersion() {
		return version;
	}

	public void setVremePocetka(Date vremePocetka) {
		this.vremePocetka = vremePocetka;
	}

	public void setVremeKraja(Date vremeKraja) {
		this.vremeKraja = vremeKraja;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
}
