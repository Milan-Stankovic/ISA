package com.isa.ISA.dbModel;

import com.isa.ISA.dbModel.enums.Zanr;

import javax.persistence.*;
import java.util.List;


/**
 * Ova klasa predstavlja Film ili Predstavu
 */
@Entity
public class Dogadjaj {

    @Id
    @GeneratedValue
    private long id;

    private String naziv;

    private int trajanje;

    @Enumerated(EnumType.STRING)
    private Zanr zanr;

    private String opis;

    private String reziser;

    @ManyToMany
    private List<Glumac> glumci; // Ne dozvoljava cist String iz nekog razloga



}
