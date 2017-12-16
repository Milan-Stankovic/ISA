package com.isa.ISA.model.oglasi;

import java.sql.Date;
import java.util.UUID;

public class Ponuda {
	private UUID id;
	private Date datum;
	private UUID ponudjac; //id korisnika koji je dao ponudu
	private int iznos;
}
