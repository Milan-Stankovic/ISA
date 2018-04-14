package com.isa.ISA.DTO;

import com.isa.ISA.dbModel.enums.TipSedista;

public class SedisteDTO {

    private int id;

    private boolean checked;

    private TipSedista tipSedista;

    public SedisteDTO(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public TipSedista getTipSedista() {
        return tipSedista;
    }

    public void setTipSedista(TipSedista tipSedista) {
        this.tipSedista = tipSedista;
    }
}
