package com.isa.ISA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.ISA.dbModel.ZvanicanRekvizit;
import com.isa.ISA.repository.DogadjajRepository;
import com.isa.ISA.repository.RekvizitRepository;

@Service
public class RekvizitService {
	@Autowired
    private RekvizitRepository rekvizitRepo;

	public List<ZvanicanRekvizit> getTematske() {
		return rekvizitRepo.findByAktivan(true);
	}

}
