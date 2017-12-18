package com.isa.ISA.rest;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("service/*")
public class Service {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response start(){
		
		URI uri = URI.create("/ISA/bioskopi.html");
		return Response.temporaryRedirect(uri).build();
	}
	
}
