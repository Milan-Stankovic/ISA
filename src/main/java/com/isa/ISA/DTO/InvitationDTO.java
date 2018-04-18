package com.isa.ISA.DTO;

public class InvitationDTO {
    private String userID;
    private Long rezID;

    public InvitationDTO() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Long getRezID() {
        return rezID;
    }

    public void setRezID(Long rezID) {
        this.rezID = rezID;
    }
}
