package com.isa.ISA.dbModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Obavestenje o rezultatu licitacije
 */
@Entity
public class Obavestenje {

    @Id
    @GeneratedValue
    private long id;
    
    private String prima;
    
    @ManyToOne
    @JsonBackReference
    private Ponuda ponuda;
    
    private boolean deleted;
    
    private String tekst;
    

	public long getId() {
		return id;
	}

	public Ponuda getPonuda() {
		return ponuda;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public String getTekst() {
		return tekst;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPonuda(Ponuda ponuda) {
		this.ponuda = ponuda;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public Obavestenje() {
	}


	public String getPrima() {
		return prima;
	}

	public void setPrima(String prima) {
		this.prima = prima;
	}
    
    
}
