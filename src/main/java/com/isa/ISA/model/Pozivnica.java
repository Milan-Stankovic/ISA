package com.isa.ISA.model;

import java.util.UUID;

import com.isa.ISA.model.enumi.Status;

public class Pozivnica {
	private Status status;
	private UUID rezervacija;
	private UUID poslao;
	private UUID prima;
	
	private int version;
	
	public Pozivnica(){
		
	}

	public Status getStatus() {
		return status;
	}

	public UUID getRezervacija() {
		return rezervacija;
	}

	public UUID getPoslao() {
		return poslao;
	}

	public UUID getPrima() {
		return prima;
	}

	public int getVersion() {
		return version;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setRezervacija(UUID rezervacija) {
		this.rezervacija = rezervacija;
	}

	public void setPoslao(UUID poslao) {
		this.poslao = poslao;
	}

	public void setPrima(UUID prima) {
		this.prima = prima;
	}

	
}
