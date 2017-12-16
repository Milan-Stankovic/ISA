package com.isa.ISA.model;

import java.util.UUID;

public class Karta {
	private UUID id;
	private UUID predstavaFilm; //ubaci id od predstave/filma
	private UUID termin; //id od termina
	private UUID sala; //id od sale
	private UUID segment;
	private UUID red;
	private UUID sediste;
	private int popust;
	
	private int version;
	
	public Karta(){
		
	}

	public UUID getId() {
		return id;
	}

	public UUID getPredstavaFilm() {
		return predstavaFilm;
	}

	public UUID getTermin() {
		return termin;
	}

	public UUID getSala() {
		return sala;
	}

	public UUID getSegment() {
		return segment;
	}

	public UUID getRed() {
		return red;
	}

	public UUID getSediste() {
		return sediste;
	}

	public int getPopust() {
		return popust;
	}

	public int getVersion() {
		return version;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setPredstavaFilm(UUID predstavaFilm) {
		this.predstavaFilm = predstavaFilm;
	}

	public void setTermin(UUID termin) {
		this.termin = termin;
	}

	public void setSala(UUID sala) {
		this.sala = sala;
	}

	public void setSegment(UUID segment) {
		this.segment = segment;
	}

	public void setRed(UUID red) {
		this.red = red;
	}

	public void setSediste(UUID sediste) {
		this.sediste = sediste;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}

	
}
