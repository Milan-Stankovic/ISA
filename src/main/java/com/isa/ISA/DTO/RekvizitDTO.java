package com.isa.ISA.DTO;

public class RekvizitDTO {

	private String naziv;
	private String opis;
	private String slika;
	private Double cena;
	private long pozBioID;
	private String admin;
	
	public RekvizitDTO() {
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

	public long getPozBioID() {
		return pozBioID;
	}

	public String getAdmin() {
		return admin;
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

	public void setPozBioID(long pozBioID) {
		this.pozBioID = pozBioID;
	}


	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	
	
}
