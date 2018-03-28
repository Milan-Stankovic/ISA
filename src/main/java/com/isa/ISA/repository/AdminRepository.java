package com.isa.ISA.repository;

import com.isa.ISA.dbModel.enums.TipAdmina;
import com.isa.ISA.dbModel.korisnici.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUserName(String username);
    Admin findById(Long id);
    List<Admin> findByTip(TipAdmina tip);
    Admin findByEmail(String email);

}
