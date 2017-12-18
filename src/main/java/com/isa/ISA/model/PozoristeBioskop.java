package com.isa.ISA.model;

import java.util.List;
import java.util.UUID;

public class PozoristeBioskop {
	private UUID id;
	private String naziv;
	private String adresa;
	private String opis;
	private double ocena;
	private List<UUID> rekviziti;
	private List<UUID> brzaRezervacija; // id karata koje su u ponudi
	private List<UUID> sale;
	private List<UUID> repertoar; //id od Predstava/filmova koji su u ponudi
	private int minBronzani; //minimalni broj bodova za status korisnika
	private int minSrebrni;
	private int minZlatni;
	
	private int version;
	
	public PozoristeBioskop(){
		
	}
	public PozoristeBioskop(String naziv, String adresa, String opis, double ocena) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.ocena = ocena;
		
	}
	public UUID getId() {
		return id;
	}
	public String getNaziv() {
		return naziv;
	}
	public String getAdresa() {
		return adresa;
	}
	public String getOpis() {
		return opis;
	}
	public double getOcena() {
		return ocena;
	}
	public List<UUID> getRekviziti() {
		return rekviziti;
	}
	public List<UUID> getBrzaRezervacija() {
		return brzaRezervacija;
	}
	public List<UUID> getSale() {
		return sale;
	}
	public List<UUID> getRepertoar() {
		return repertoar;
	}
	public int getMinSrebrni() {
		return minSrebrni;
	}
	public int getMinBronzani() {
		return minBronzani;
	}
	public int getMinZlatni() {
		return minZlatni;
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
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
	public void setRekviziti(List<UUID> rekviziti) {
		this.rekviziti = rekviziti;
	}
	public void setBrzaRezervacija(List<UUID> brzaRezervacija) {
		this.brzaRezervacija = brzaRezervacija;
	}
	public void setSale(List<UUID> sale) {
		this.sale = sale;
	}
	public void setRepertoar(List<UUID> sale) {
		this.repertoar = repertoar;
	}

	public void setMinBronzani(int minBronzani) {
		this.minBronzani = minBronzani;
	}

	public void setMinSrebrni(int minSrebrni) {
		this.minSrebrni = minSrebrni;
	}

	public void setMinZlatni(int minZlatni) {
		this.minZlatni = minZlatni;
	}
	
	
	
}
