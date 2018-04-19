package com.isa.ISA.repository;

import com.isa.ISA.dbModel.Encryption;
import org.springframework.data.repository.CrudRepository;

public interface EncryptionRepository  extends CrudRepository<Encryption,Long> {

    Encryption findByKorisnikID(Long id);
}
