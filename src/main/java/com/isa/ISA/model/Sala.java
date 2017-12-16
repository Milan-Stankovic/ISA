package com.isa.ISA.model;

import java.util.List;
import java.util.UUID;

public class Sala {
	private UUID id;
	private String naziv;
	private List<UUID> segmenti;
	
	private int version;
	
	public Sala(){
		
	}

	public UUID getId() {
		return id;
	}

	public String getNaziv() {
		return naziv;
	}

	public List<UUID> getSegmenti() {
		return segmenti;
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

	public void setSegmenti(List<UUID> segmenti) {
		this.segmenti = segmenti;
	}
	
	
}
