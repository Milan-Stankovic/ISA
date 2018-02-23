package com.isa.ISA.rest;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.isa.ISA.IsaApplication;
import com.isa.ISA.model.PozoristeBioskop;

@Path("getData/")
public class getData {


	@Path("Bioskopi")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<PozoristeBioskop> start(){
		return IsaApplication.getBioskopi();
	}
}
