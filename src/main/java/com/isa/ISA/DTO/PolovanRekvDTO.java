package com.isa.ISA.DTO;

import java.sql.Date;

public class PolovanRekvDTO {
	private String naziv;
	private String opis;
	private String slika;
	private Double cena;
	private String username;
	private Date datum;
	
	public PolovanRekvDTO() {
	}

	public String getNaziv() {
		return naziv;
	}

	public String getOpis() {
		return opis;
	}

	public String getSlika() {
		return slika;
	}

	public Double getCena() {
		return cena;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public String getUsername() {
		return username;
	}

	public Date getDatum() {
		return datum;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	
	
	
}
