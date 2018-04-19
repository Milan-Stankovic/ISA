package com.isa.ISA.DTO;

public class TransakcijaDTO {
    private String message;

    private Long pozivId;

    public TransakcijaDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getPozivId() {
        return pozivId;
    }

    public void setPozivId(Long pozivId) {
        this.pozivId = pozivId;
    }
}
