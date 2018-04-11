package com.isa.ISA.repository;

import com.isa.ISA.dbModel.CenovnikSedista;
import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.dbModel.enums.TipSedista;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CenovnikSedistaRepository extends CrudRepository<CenovnikSedista, Long> {
    List<CenovnikSedista> findByTip(TipSedista tip);
    List<CenovnikSedista> findByUstanova(PozoristeBioskop id); // Ne znam da li mu je ovako ok ili ce traziti ceo objekat
    List<CenovnikSedista> findBySala(Sala id);
    List<CenovnikSedista> findByUstanovaAndSala(PozoristeBioskop id, Sala s);
    List<CenovnikSedista> findByUstanovaAndTip(PozoristeBioskop id, TipSedista tip);
    List<CenovnikSedista> findBySalaAndTip(Sala id, TipSedista tip);
    List<CenovnikSedista> findByUstanovaAndSalaAndTip(PozoristeBioskop id, Sala id2, TipSedista tip);
}
