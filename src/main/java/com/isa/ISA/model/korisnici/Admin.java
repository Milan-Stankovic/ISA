package com.isa.ISA.model.korisnici;

import java.util.UUID;

public class Admin {
	private UUID id;
	private String email;
	private String lozinka;
	private Uloga uloga;
	
	private int version;
	
	public Admin(){
		
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

	public Uloga getUloga() {
		return uloga;
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

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
