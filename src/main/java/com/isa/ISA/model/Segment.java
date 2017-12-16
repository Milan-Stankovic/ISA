package com.isa.ISA.model;

import java.util.List;
import java.util.UUID;

public class Segment {
	private UUID id;
	private String naziv;
	private int redovi;
	private int sedista;
	
	private int version;
	
	public Segment(){
		
	}

	public UUID getId() {
		return id;
	}

	public String getNaziv() {
		return naziv;
	}

	public int getRedovi() {
		return redovi;
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

	public void setRedovi(int redovi) {
		this.redovi = redovi;
	}

}
