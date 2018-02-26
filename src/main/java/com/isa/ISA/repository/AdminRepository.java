package com.isa.ISA.repository;

import com.isa.ISA.dbModel.korisnici.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUserName(String username);
}
