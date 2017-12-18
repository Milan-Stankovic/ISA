package com.isa.ISA;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.isa.ISA.model.PozoristeBioskop;

@SpringBootApplication
public class IsaApplication {
	private static ArrayList<PozoristeBioskop> bioskopi;
	
	public static void main(String[] args) {
		SpringApplication.run(IsaApplication.class, args);
		 
	}
	
	public static ArrayList<PozoristeBioskop> getBioskopi(){
		if(bioskopi==null){
			bioskopi = new ArrayList<>();
			bioskopi.add(new PozoristeBioskop("bioskop1","adresa1", "opis1", 5));
		}
		return bioskopi;
	}
}
