package com.isa.ISA.DTO;

public class OneClickAfterDTO {

    private Long userId;

    private Long pozivId;

    private Long rezervacijaID;


    public OneClickAfterDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPozivId() {
        return pozivId;
    }

    public void setPozivId(Long pozivId) {
        this.pozivId = pozivId;
    }

    public Long getRezervacijaID() {
        return rezervacijaID;
    }

    public void setRezervacijaID(Long rezervacijaID) {
        this.rezervacijaID = rezervacijaID;
    }
}
