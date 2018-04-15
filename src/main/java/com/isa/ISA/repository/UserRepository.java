package com.isa.ISA.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;

public interface UserRepository extends CrudRepository<RegistrovaniKorisnik, Long> {
    RegistrovaniKorisnik findByUserName(String username);
    RegistrovaniKorisnik findById(Long id);
    RegistrovaniKorisnik findByEmail(String email);
    List<RegistrovaniKorisnik> findByImeIgnoreCase(String ime);
    List<RegistrovaniKorisnik> findByPrezimeIgnoreCase(String prezime);
    List<RegistrovaniKorisnik> findByImeAndPrezimeIgnoreCase(String ime, String prezime);


}
