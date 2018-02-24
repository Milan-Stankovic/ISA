package com.isa.ISA.dbModel;


import com.isa.ISA.dbModel.enums.TipUstanove;

import javax.persistence.*;

@Entity
public class PozoristeBioskop {
    @Id
    @GeneratedValue
    private long id;

    private String naziv;

    private String opis;

    private String adressa;

    private int bronzeTreshold;

    private int silverTreshold;

    private int goldTreshold;

    private int bronzePopust;

    private int silverPopust;

    private int goldPopust;

    @Enumerated(EnumType.STRING)
    private TipUstanove tip;




}
