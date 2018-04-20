package com.isa.ISA.regUserTests;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ISA.DTO.RegKorDTO;
import com.isa.ISA.DTO.RezervacijaDTO;
import com.isa.ISA.controller.ReservationController;
import com.isa.ISA.controller.SettingsController;
import com.isa.ISA.dbModel.enums.StatusNaloga;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.service.*;
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
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SettingsController.class, secure = false)
public class SettingsTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SettingsService settingsService;

    @Autowired
    private WebApplicationContext webApplicationContext;



    private ObjectMapper jsonMapper= new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private RegistrovaniKorisnik k;
    private RegistrovaniKorisnik rk3;
    private ArrayList<RegistrovaniKorisnik> ret;

    @PostConstruct
    public void set() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @MockBean
    private RegKorDTO rk4;

    @Before
    public void setup() {
        rk4 = new RegKorDTO();
        rk4.setUserName("zika");
        rk4.setPassword("zika");
        rk4.setEmail("zika@gmail.com");
        rk4.setBrojTelefona("4444");
        rk4.setGrad("zika");
        rk4.setIme("Zika");
        rk4.setPrezime("Zikic");

        k = new RegistrovaniKorisnik();
        k.setUserName("zika");
        k.setPassword("zika");
        k.setEmail("zika@gmail.com");
        k.setStatus(StatusNaloga.AKTIVAN);
        k.setBrojTelefona("4444");
        k.setGrad("zika");
        k.setIme("Zika");
        k.setPrezime("Zikic");

        rk3 = new RegistrovaniKorisnik();
        rk3.setUserName("f");
        rk3.setPassword("f");
        rk3.setEmail("mali.patuljko@gmail.com");
        rk3.setStatus(StatusNaloga.AKTIVAN);
        rk3.setBrojTelefona("4444");
        rk3.setGrad("f");
        rk3.setIme("f");
        rk3.setPrezime("f");


        ret = new ArrayList<>();
        ret.add(k);
        ret.add(rk3);
    }

    @Test
    public void saveR() throws Exception{
        Mockito.when(settingsService.saveReg(Mockito.any(RegKorDTO.class))).thenReturn("y");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/settings/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(new RegKorDTO()))
        ).andReturn();
        String arr =mvcResult.getResponse().getContentAsString();

        System.out.println(arr);
        Assert.assertEquals("y", arr);

    }

    @Test
    public void saveAdm() throws Exception{
        Mockito.when(settingsService.saveAdm(Mockito.any(RegKorDTO.class))).thenReturn("y");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/settings/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(new RegKorDTO()))
        ).andReturn();
        String arr =mvcResult.getResponse().getContentAsString();

        System.out.println(arr);
        Assert.assertEquals("y", arr);

    }

    @Test
    public void search() throws Exception{
        Mockito.when(settingsService.search(Mockito.anyString())).thenReturn(ret);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/settings/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString("test"))
        ).andReturn();
        String arr =mvcResult.getResponse().getContentAsString();

        System.out.println(arr);
        Assert.assertTrue(arr.contains("\"ime\":\"Zika\"") && arr.contains("\"ime\":\"f\""));

    }

}
