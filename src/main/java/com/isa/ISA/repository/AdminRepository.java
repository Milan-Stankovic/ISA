package com.isa.ISA.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isa.ISA.dbModel.enums.TipAdmina;
import com.isa.ISA.dbModel.korisnici.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUserName(String username);
    Admin findById(Long id);
    List<Admin> findByTip(TipAdmina tip);
    Admin findByEmail(String email);

}
