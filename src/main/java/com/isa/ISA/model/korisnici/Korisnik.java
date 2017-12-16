package com.isa.ISA.model.korisnici;

import java.util.List;
import java.util.UUID;

public class Korisnik{
	private UUID id;
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private String grad;
	private int telefon;
	private Clan status;
	private int bodovi;
	private List<UUID> friends;
	private List<UUID> pozivi; //id svih PRIMLJENIH pozivnica
	private List<UUID> obavestenja; //id svih obavestenja
	private List<UUID> rezervacije; //id svih iniciranih rezervacija
	private List<UUID> istorija; //id svake istorije -> ocena i predstava/projekcija
	
	private int version;
	
	
	
	public Korisnik(){
		
	}


	public UUID getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	public String getLozinka() {
		return lozinka;
	}
	public String getIme() {
		return ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public String getGrad() {
		return grad;
	}
	public int getTelefon() {
		return telefon;
	}
	public Clan getStatus() {
		return status;
	}
	public int getBodovi() {
		return bodovi;
	}
	public List<UUID> getFriends() {
		return friends;
	}
	public List<UUID> getPozivi() {
		return pozivi;
	}
	public List<UUID> getObavestenja() {
		return obavestenja;
	}
	public List<UUID> getRezervacije() {
		return rezervacije;
	}
	public List<UUID> getIstorija() {
		return istorija;
	}
	public int getVersion() {
		return version;
	}



	public void setId(UUID id) {
		this.id = id;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	public void setTelefon(int telefon) {
		this.telefon = telefon;
	}
	public void setStatus(Clan status) {
		this.status = status;
	}
	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}
	public void setFriends(List<UUID> friends) {
		this.friends = friends;
	}
	public void setPozivi(List<UUID> pozivi) {
		this.pozivi = pozivi;
	}
	public void setObavestenja(List<UUID> obavestenja) {
		this.obavestenja = obavestenja;
	}
	public void setRezervacije(List<UUID> rezervacije) {
		this.rezervacije = rezervacije;
	}



	public void setIstorija(List<UUID> istorija) {
		this.istorija = istorija;
	}

}
