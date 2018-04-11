package com.isa.ISA.repository;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;

public interface UserRepository extends CrudRepository<RegistrovaniKorisnik, Long> {
    RegistrovaniKorisnik findByUserName(String username);
    RegistrovaniKorisnik findByEmail(String email);
}
