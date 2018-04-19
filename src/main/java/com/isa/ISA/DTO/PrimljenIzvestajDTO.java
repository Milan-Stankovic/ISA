package com.isa.ISA.DTO;

import java.util.List;


public class PrimljenIzvestajDTO {

    private String ocenaPb;

    private List<String> ocenaDogadjaja;

    private String zaradaOdRekvizita;

    private String zaradaOdKarata;

    private String ukupnaZarada;

    private List<Integer> grafikPosetaDnevno;

    private List<Integer> grafikPosetaNedeljno;

    private List<Integer> grafikPosetaMesecno;

    private int prviDan;

    private int mesec;

    private int godina;

    public PrimljenIzvestajDTO() {
    }

    public int getPrviDan() {
        return prviDan;
    }

    public void setPrviDan(int prviDan) {
        this.prviDan = prviDan;
    }

    public int getMesec() {
        return mesec;
    }

    public void setMesec(int mesec) {
        this.mesec = mesec;
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public String getOcenaPb() {
        return ocenaPb;
    }

    public void setOcenaPb(String ocenaPb) {
        this.ocenaPb = ocenaPb;
    }

    public List<String> getOcenaDogadjaja() {
        return ocenaDogadjaja;
    }

    public void setOcenaDogadjaja(List<String> ocenaDogadjaja) {
        this.ocenaDogadjaja = ocenaDogadjaja;
    }

    public String getZaradaOdRekvizita() {
        return zaradaOdRekvizita;
    }

    public void setZaradaOdRekvizita(String zaradaOdRekvizita) {
        this.zaradaOdRekvizita = zaradaOdRekvizita;
    }

    public String getZaradaOdKarata() {
        return zaradaOdKarata;
    }

    public void setZaradaOdKarata(String zaradaOdKarata) {
        this.zaradaOdKarata = zaradaOdKarata;
    }

    public String getUkupnaZarada() {
        return ukupnaZarada;
    }

    public void setUkupnaZarada(String ukupnaZarada) {
        this.ukupnaZarada = ukupnaZarada;
    }

    public List<Integer> getGrafikPosetaDnevno() {
        return grafikPosetaDnevno;
    }

    public void setGrafikPosetaDnevno(List<Integer> grafikPosetaDnevno) {
        this.grafikPosetaDnevno = grafikPosetaDnevno;
    }

    public List<Integer> getGrafikPosetaNedeljno() {
        return grafikPosetaNedeljno;
    }

    public void setGrafikPosetaNedeljno(List<Integer> grafikPosetaNedeljno) {
        this.grafikPosetaNedeljno = grafikPosetaNedeljno;
    }

    public List<Integer> getGrafikPosetaMesecno() {
        return grafikPosetaMesecno;
    }

    public void setGrafikPosetaMesecno(List<Integer> grafikPosetaMesecno) {
        this.grafikPosetaMesecno = grafikPosetaMesecno;
    }
}
