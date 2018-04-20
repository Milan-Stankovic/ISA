package com.isa.ISA.regUserTests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ISA.DTO.RezervacijaDTO;
import com.isa.ISA.controller.ReservationController;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.KartaRepository;
import com.isa.ISA.repository.PozivRepository;
import com.isa.ISA.service.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ReservationController.class, secure = false)
public class ReserveTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private RezervacijaService rezService;

    @MockBean
    private SalaService salaService;

    @MockBean
    private UserService userService;

    @MockBean
    private ProjekcijaService projekcijaService;

    @MockBean
    private PozivRepository pozRepo;

    @MockBean
    private KartaRepository kartaRepo;

    @MockBean
    private EmailService em;


    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper jsonMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @PostConstruct
    public void set() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void res() throws Exception {
        Mockito.when(reservationService.reserve(Mockito.any(RezervacijaDTO.class))).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(new RezervacijaDTO()))
        ).andReturn();
        String arr = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals("true",arr);


    }

}
