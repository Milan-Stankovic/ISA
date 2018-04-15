package com.isa.ISA.DTO;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.dbModel.enums.TipUstanove;
import com.isa.ISA.dbModel.korisnici.Admin;

public class PozoristeBioskopDTO {
	private String naziv;
    private String opis;
    private String adresa;
    private String urlMape;
    private TipUstanove tip;
	public String getNaziv() {
		return naziv;
	}
	public String getOpis() {
		return opis;
	}
	public String getAdresa() {
		return adresa;
	}
	public String getUrlMape() {
		return urlMape;
	}
	public TipUstanove getTip() {
		return tip;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public void setUrlMape(String urlMape) {
		this.urlMape = urlMape;
	}
	public void setTip(TipUstanove tip) {
		this.tip = tip;
	}
	public PozoristeBioskopDTO() {
	}
    
    

}
