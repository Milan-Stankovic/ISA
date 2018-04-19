package com.isa.ISA.DTO;

public class InvitationDTO {

    private Long rezID;

    private boolean isAccepted;

    public InvitationDTO() {
    }

    public InvitationDTO(Long rezID, boolean isAccepted) {
        this.rezID = rezID;
        this.isAccepted = isAccepted;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Long getRezID() {
        return rezID;
    }

    public void setRezID(Long rezID) {
        this.rezID = rezID;
    }
}
