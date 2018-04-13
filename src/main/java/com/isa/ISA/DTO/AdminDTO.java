package com.isa.ISA.DTO;

import java.util.ArrayList;

import com.isa.ISA.dbModel.enums.TipAdmina;

public class AdminDTO {
	private String username;
	private String pass;
	private TipAdmina tipAdmina;
	private long[] pozBio;
	private String email;
	
	public AdminDTO() {
	}

	public String getUsername() {
		return username;
	}

	public String getPass() {
		return pass;
	}

	public TipAdmina getTipAdmina() {
		return tipAdmina;
	}

	public long[] getPozBio() {
		return pozBio;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setTipAdmina(TipAdmina tipAdmina) {
		this.tipAdmina = tipAdmina;
	}

	public void setPozBio(long[] pozBio) {
		this.pozBio = pozBio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
