package com.isa.ISA.DTO;

public class RegKorDTO {

    private String userName;
    private String password;
    private String ime;
    private String prezime;
    private String email;
    private String grad;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    private String brojTelefona;

    public RegKorDTO(){}

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getEmail() {
        return email;
    }

    public String getGrad() {
        return grad;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }
}
