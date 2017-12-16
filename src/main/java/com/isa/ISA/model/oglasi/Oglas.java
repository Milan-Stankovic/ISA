package com.isa.ISA.model.oglasi;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import com.isa.ISA.model.enumi.Status;

public class Oglas {
	private UUID id;
	private UUID autor;
	private String naziv;
	private String opis;
	private Date aktivanDo;
	private String slika;
	private Status status;
	private List<UUID> ponude; //lista id-eva ponuda
	
	private int version;
	
	public Oglas(){
		
	}

	public UUID getId() {
		return id;
	}

	public UUID getAutor() {
		return autor;
	}

	public String getNaziv() {
		return naziv;
	}

	public String getOpis() {
		return opis;
	}

	public Date getAktivanDo() {
		return aktivanDo;
	}

	public String getSlika() {
		return slika;
	}

	public Status getStatus() {
		return status;
	}

	public List<UUID> getPonude() {
		return ponude;
	}

	public int getVersion() {
		return version;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setAutor(UUID autor) {
		this.autor = autor;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public void setAktivanDo(Date aktivanDo) {
		this.aktivanDo = aktivanDo;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setPonude(List<UUID> ponude) {
		this.ponude = ponude;
	}

	
}
