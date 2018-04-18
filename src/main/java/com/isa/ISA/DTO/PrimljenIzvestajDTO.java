package com.isa.ISA.DTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PrimljenIzvestajDTO {

    private String ocenaPb;

    private List<String> ocenaDogadjaja;

    private String zaradaOdRekvizita;

    private String zaradaOdKarata;

    private String ukupnaZarada;

    private Map<Date, Integer> grafikPosetaDnevo;

    private Map<Integer, Integer> grafikPosetaNedeljno;

    private Map<Integer, Integer> grafikPosetaMesecno;

    public PrimljenIzvestajDTO() {
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

    public Map<Date, Integer> getGrafikPosetaDnevo() {
        return grafikPosetaDnevo;
    }

    public void setGrafikPosetaDnevo(Map<Date, Integer> grafikPosetaDnevo) {
        this.grafikPosetaDnevo = grafikPosetaDnevo;
    }

    public Map<Integer, Integer> getGrafikPosetaNedeljno() {
        return grafikPosetaNedeljno;
    }

    public void setGrafikPosetaNedeljno(Map<Integer, Integer> grafikPosetaNedeljno) {
        this.grafikPosetaNedeljno = grafikPosetaNedeljno;
    }

    public Map<Integer, Integer> getGrafikPosetaMesecno() {
        return grafikPosetaMesecno;
    }

    public void setGrafikPosetaMesecno(Map<Integer, Integer> grafikPosetaMesecno) {
        this.grafikPosetaMesecno = grafikPosetaMesecno;
    }
}
