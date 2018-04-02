package com.isa.ISA.service;

import com.isa.ISA.dbModel.Rezervacija;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service

@Transactional(readOnly = true)
public class OneClickReservationService {

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            readOnly = false)
    public Rezervacija reserveSeat(Rezervacija r){

        return r;
    }
}
