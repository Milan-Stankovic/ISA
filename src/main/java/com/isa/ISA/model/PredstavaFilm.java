package com.isa.ISA.model;

import java.util.List;
import java.util.UUID;

import com.isa.ISA.model.enumi.Zanr;

public class PredstavaFilm {
	private UUID id;
	private String naziv;
	private List<String> glumac;
	private List<Zanr> zanr;
	private List<String> reditelj;
	private int trajanje;
	private String poster;
	private double ocena;
	private List<UUID> termini;
	private List<UUID> sale;
	private List<UUID> zatvoreniSegment; //admin bioskopa/pozorista moze da zatvori neki segment
	
	private int version;
	
	public PredstavaFilm(){
		
	}
	public UUID getId() {
		return id;
	}
	public String getNaziv() {
		return naziv;
	}

	public List<String> getGlumac() {
		return glumac;
	}

	public List<Zanr> getZanr() {
		return zanr;
	}

	public List<String> getReditelj() {
		return reditelj;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public String getPoster() {
		return poster;
	}

	public double getOcena() {
		return ocena;
	}

	public List<UUID> getTermini() {
		return termini;
	}

	public List<UUID> getSale() {
		return sale;
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

	public void setGlumac(List<String> glumac) {
		this.glumac = glumac;
	}

	public void setZanr(List<Zanr> zanr) {
		this.zanr = zanr;
	}

	public void setReditelj(List<String> reditelj) {
		this.reditelj = reditelj;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}

	public void setTermini(List<UUID> termini) {
		this.termini = termini;
	}

	public void setSale(List<UUID> sale) {
		this.sale = sale;
	}

	
	
	
}
