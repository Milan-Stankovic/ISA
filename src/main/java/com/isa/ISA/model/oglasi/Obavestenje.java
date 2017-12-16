package com.isa.ISA.model.oglasi;

import java.util.UUID;

import com.isa.ISA.model.enumi.Tip;

public class Obavestenje {
	private UUID id;
	private UUID salje; //id korisnika koji salje obavestenje/zahtev
	private UUID namenjeno; // id korisnika koji prima obavestenje
	private String tekst;
	private boolean seen;
	private Tip tip;
	
	private int version;
	
	public Obavestenje(){
		
	}

	public UUID getId() {
		return id;
	}
	public UUID getSalje() {
		return salje;
	}
	public UUID getNamenjeno() {
		return namenjeno;
	}

	public String getTekst() {
		return tekst;
	}
	public Tip getTip() {
		return tip;
	}
	public boolean isSeen() {
		return seen;
	}

	public int getVersion() {
		return version;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setNamenjeno(UUID namenjeno) {
		this.namenjeno = namenjeno;
	}

	public void setSalje(UUID salje) {
		this.salje = salje;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	public void setTip(Tip tip) {
		this.tip = tip;
	}


}
