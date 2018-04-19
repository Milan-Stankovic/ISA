package com.isa.ISA.service;

import com.isa.ISA.dbModel.Encryption;
import com.isa.ISA.repository.EncryptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Service
public class EncryptionService {

    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;


    @Autowired
    private EncryptionRepository encRepo;

    public void addEncr(Encryption e){ encRepo.save(e); }

    public Encryption getEncr(Long id){ return encRepo.findOne(id); }

    public Encryption getEncrUser(Long id){ return encRepo.findByKorisnikID(id); }

    public static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    public byte[] makeDigest(String value, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        return md.digest(value.getBytes());
    }

}
