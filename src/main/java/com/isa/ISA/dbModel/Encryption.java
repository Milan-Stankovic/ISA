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
    private byte[] encryptedPass;
    @Column(nullable = false)
    private byte[] salt;
    @Column( unique = true, nullable = false)
    private Long korisnikID;

    public Encryption() {
    }

    public Encryption(byte[] encryptedPass, byte[] salt, Long korisnikID) {
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

    public byte[] getEncryptedPass() {
        return encryptedPass;
    }

    public void setEncryptedPass(byte[] encryptedPass) {
        this.encryptedPass = encryptedPass;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public Long getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(Long korisnikID) {
        this.korisnikID = korisnikID;
    }
}
