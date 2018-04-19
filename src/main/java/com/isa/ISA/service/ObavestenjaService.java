package com.isa.ISA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.ISA.dbModel.Obavestenje;
import com.isa.ISA.repository.DogadjajRepository;
import com.isa.ISA.repository.ObavestenjeRepository;

@Service
public class ObavestenjaService {
	 @Autowired
	 private ObavestenjeRepository obavRepo;

	public List<Obavestenje> getObavestenja(String username) {
		return obavRepo.findByPrimaAndDeleted(username, false);
	}

	public Obavestenje deleteObavestenja(long id) {
		Obavestenje retVal = obavRepo.findOne(id);
		retVal.setDeleted(true);
		return obavRepo.save(retVal);
	}
}
