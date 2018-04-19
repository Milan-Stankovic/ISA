package com.isa.ISA.dbModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Encryption {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String encryptedPass;
    @Column(nullable = false)
    private String salt;
    @Column( unique = true, nullable = false)
    private Long korisnikID;

    public Encryption() {
    }

    public Encryption(String encryptedPass, String salt, Long korisnikID) {
        this.encryptedPass = encryptedPass;
        this.salt = salt;
        this.korisnikID = korisnikID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEncryptedPass() {
        return encryptedPass;
    }

    public void setEncryptedPass(String encryptedPass) {
        this.encryptedPass = encryptedPass;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(Long korisnikID) {
        this.korisnikID = korisnikID;
    }
}
