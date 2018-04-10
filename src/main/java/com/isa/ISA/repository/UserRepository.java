package com.isa.ISA.repository;

import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<RegistrovaniKorisnik, Long> {
    RegistrovaniKorisnik findByUserName(String username);
    RegistrovaniKorisnik findByEmail(String email);
}
