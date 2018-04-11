package com.isa.ISA.dbModel.korisnici;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.isa.ISA.dbModel.enums.StatusNaloga;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Korisnik {

    @Id
    @GeneratedValue
    protected long id;

    @Column(nullable = false)
    protected String userName;

    @Column(nullable = false)
    protected String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected StatusNaloga status;

    protected String ime;

    protected String prezime;

    @Column( unique = true, nullable = false)
    protected String email;

    protected String brojTelefona;
    
    protected String grad;

    public Korisnik(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StatusNaloga getStatus() {
        return status;
    }

    public void setStatus(StatusNaloga status) {
        this.status = status;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}
    
    
}
