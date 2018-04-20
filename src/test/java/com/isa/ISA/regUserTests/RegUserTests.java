package com.isa.ISA.regUserTests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ISA.controller.UserController;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.enums.StatusNaloga;
import com.isa.ISA.dbModel.enums.StatusPrijateljstva;
import com.isa.ISA.dbModel.korisnici.Prijatelj;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.UserRepository;
import com.isa.ISA.service.AdminService;
import com.isa.ISA.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class RegUserTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void set() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public static String json(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(object);
    }

    private RegistrovaniKorisnik rk4;
    @Before
    public void setup(){
        rk4 = new RegistrovaniKorisnik();
        rk4.setUserName("zika");
        rk4.setPassword("zika");
        rk4.setEmail("zika@gmail.com");
        rk4.setStatus(StatusNaloga.AKTIVAN);
        rk4.setBrojTelefona("4444");
        rk4.setGrad("zika");
        rk4.setIme("Zika");
        rk4.setPrezime("Zikic");

        RegistrovaniKorisnik rk3 = new RegistrovaniKorisnik();
        rk3.setUserName("f");
        rk3.setPassword("f");
        rk3.setEmail("mali.patuljko@gmail.com");
        rk3.setStatus(StatusNaloga.AKTIVAN);
        rk3.setBrojTelefona("4444");
        rk3.setGrad("f");
        rk3.setIme("f");
        rk3.setPrezime("f");

        Rezervacija r = new Rezervacija();
        r.setUrezervaciji(new ArrayList<>());
        r.setProjekcija(new Projekcija());
        r.setPopust(50);
        r.setRezervisao(rk4);

        rk4.setRezervacije(Arrays.asList(r));

        Prijatelj p = new Prijatelj();
        p.setStatus(StatusPrijateljstva.PRIHVACENO);
        p.setPrimalac(rk4);
        p.setPosiljalac(rk3);
        rk4.setPrijatelji(Arrays.asList(p));
    }

    @Test
    public void getUser() throws Exception {
        Mockito.when(userService.getUser(Mockito.anyString())).thenReturn(rk4);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/test")).andReturn();
        String arr =mvcResult.getResponse().getContentAsString();

        System.out.println(arr);
        Assert.assertTrue(arr.contains("\"ime\":\"Zika\""));


    }
    @Test
    public void getUserReservations() throws Exception {
        Mockito.when(userService.getAllReservations(Mockito.anyString())).thenReturn(rk4.getRezervacije());
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/reservations/test")).andReturn();
        String arr =mvcResult.getResponse().getContentAsString();

        Assert.assertTrue(arr.contains(",\"popust\":50"));

    }








}
