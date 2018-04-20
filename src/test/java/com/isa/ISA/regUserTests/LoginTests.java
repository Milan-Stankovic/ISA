package com.isa.ISA.regUserTests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ISA.DTO.RegKorDTO;
import com.isa.ISA.controller.LoginController;
import com.isa.ISA.dbModel.enums.StatusNaloga;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.service.LoginService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LoginController.class, secure = false)
public class LoginTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @PostConstruct
    public void set() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private RegistrovaniKorisnik rk4;
    private RegKorDTO rk4DTO;

    @Before
    public void setup() {
        rk4 = new RegistrovaniKorisnik();
        rk4.setUserName("zika");
        rk4.setPassword("zika");
        rk4.setEmail("zika@gmail.com");
        rk4.setStatus(StatusNaloga.AKTIVAN);
        rk4.setBrojTelefona("4444");
        rk4.setGrad("zika");
        rk4.setIme("Zika");
        rk4.setPrezime("Zikic");

        rk4DTO = new RegKorDTO();
    }

    @Test
    public void login() throws Exception {
        Mockito.when(loginService.login(Mockito.any(RegKorDTO.class))).thenReturn(rk4);
        ObjectMapper jsonMapper= new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(rk4DTO)))
                .andReturn();
        String arr =mvcResult.getResponse().getContentAsString();

        System.out.println(arr);
        Assert.assertTrue(arr.contains("\"ime\":\"Zika\""));
    }



    }
