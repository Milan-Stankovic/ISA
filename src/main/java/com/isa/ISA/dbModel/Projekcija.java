package com.isa.ISA.dbModel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Projekcija {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany
    private List<Rezervacija> rezervacije;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Sala sala;

    @Column(nullable = false)
    private Date vreme;

    @Column(nullable = false)
    private double cena;





}
