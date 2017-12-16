package com.isa.ISA.model;

import java.util.UUID;

public class Istorija {
	private UUID id;
	private UUID rezervacija; //na koju se rezervaciju odnosi istorija
	private int ocenaP; //ocena projekcije/predstave
	private int ocenaAmbijenta;
	
	private int version;
	
	public Istorija(){
		
	}

	public UUID getId() {
		return id;
	}

	public UUID getRezervacija() {
		return rezervacija;
	}

	public int getOcenaP() {
		return ocenaP;
	}

	public int getOcenaAmbijenta() {
		return ocenaAmbijenta;
	}

	public int getVersion() {
		return version;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setRezervacija(UUID rezervacija) {
		this.rezervacija = rezervacija;
	}

	public void setOcenaP(int ocenaP) {
		this.ocenaP = ocenaP;
	}

	public void setOcenaAmbijenta(int ocenaAmbijenta) {
		this.ocenaAmbijenta = ocenaAmbijenta;
	}

}
