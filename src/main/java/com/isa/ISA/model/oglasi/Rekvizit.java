package com.isa.ISA.model.oglasi;

import java.util.UUID;

public class Rekvizit {
	private UUID id;
	private String naziv;
	private String opis;
	private int cena;
	private String slika;
	
	private int version;
	
	public Rekvizit(){
		
	}

	public UUID getId() {
		return id;
	}

	public String getNaziv() {
		return naziv;
	}

	public String getOpis() {
		return opis;
	}

	public int getCena() {
		return cena;
	}

	public String getSlika() {
		return slika;
	}

	public int getVersion() {
		return version;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	
	
}
