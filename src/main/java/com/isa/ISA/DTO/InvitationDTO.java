package com.isa.ISA.DTO;

public class InvitationDTO {

    private Long rezID;

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    private boolean isAccepted;

    public InvitationDTO() {
    }

    public Long getRezID() {
        return rezID;
    }

    public void setRezID(Long rezID) {
        this.rezID = rezID;
    }
}
