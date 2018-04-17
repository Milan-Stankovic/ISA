package com.isa.ISA.DTO;

public class PonudaDTO {
	private long idRekvizita;
	private String username;
	private double cena;
	public long getIdRekvizita() {
		return idRekvizita;
	}
	public String getUsername() {
		return username;
	}
	public double getCena() {
		return cena;
	}
	public void setIdRekvizita(long idRekvizita) {
		this.idRekvizita = idRekvizita;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public PonudaDTO() {
	}
	
	
}
