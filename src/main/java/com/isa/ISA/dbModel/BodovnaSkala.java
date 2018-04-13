package com.isa.ISA.dbModel;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BodovnaSkala {

    @Id
    @GeneratedValue
	private long id;

    private int bronzeTreshold;

    private int silverTreshold;

    private int goldTreshold;

    private int bronzePopust;

    private int silverPopust;

    private int goldPopust;
    
    private Date datum;

	public BodovnaSkala() {
	}

	public long getId() {
		return id;
	}

	public int getBronzeTreshold() {
		return bronzeTreshold;
	}

	public int getSilverTreshold() {
		return silverTreshold;
	}

	public int getGoldTreshold() {
		return goldTreshold;
	}

	public int getBronzePopust() {
		return bronzePopust;
	}

	public int getSilverPopust() {
		return silverPopust;
	}

	public int getGoldPopust() {
		return goldPopust;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setBronzeTreshold(int bronzeTreshold) {
		this.bronzeTreshold = bronzeTreshold;
	}

	public void setSilverTreshold(int silverTreshold) {
		this.silverTreshold = silverTreshold;
	}

	public void setGoldTreshold(int goldTreshold) {
		this.goldTreshold = goldTreshold;
	}

	public void setBronzePopust(int bronzePopust) {
		this.bronzePopust = bronzePopust;
	}

	public void setSilverPopust(int silverPopust) {
		this.silverPopust = silverPopust;
	}

	public void setGoldPopust(int goldPopust) {
		this.goldPopust = goldPopust;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}
    
    }
