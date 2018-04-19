package com.isa.ISA.service;

import com.isa.ISA.dbModel.Encryption;
import com.isa.ISA.repository.EncryptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    @Autowired
    private EncryptionRepository encRepo;

    public void addEncr(Encryption e){ encRepo.save(e); }

    public Encryption getEncr(Long id){ return encRepo.findOne(id); }

    public Encryption getEncrUser(Long id){ return encRepo.findByKorisnikID(id); }
}
