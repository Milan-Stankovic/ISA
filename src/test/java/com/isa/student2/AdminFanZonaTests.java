package com.isa.student2;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ISA.IsaApplication;
import com.isa.ISA.DTO.AdminDTO;
import com.isa.ISA.DTO.RegKorDTO;
import com.isa.ISA.DTO.RezervacijaDTO;
import com.isa.ISA.controller.AdminController;
import com.isa.ISA.dbModel.enums.TipAdmina;
import com.isa.ISA.dbModel.korisnici.Admin;
import com.isa.ISA.service.AdminService;
import com.mysql.fabric.xmlrpc.base.Array;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = IsaApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class AdminFanZonaTests {
	@Autowired
    MockMvc mockMvc;
	
	private ObjectMapper jsonMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private static int id = 1; 
    @Before
    public void setup() throws Exception{
      AdminDTO adm = new AdminDTO();
      adm.setEmail("adm10@a.com");
      adm.setPass("default");
      adm.setPozBio(new long[0]);
      adm.setTipAdmina(TipAdmina.FAN);
      adm.setUsername("fan10");
      MvcResult mvcResult = mockMvc.perform(
              MockMvcRequestBuilders.post("/admins")
                      .contentType(contentType)
                      .content(jsonMapper.writeValueAsString(adm)))
              .andReturn();
      JSONObject result = new JSONObject( mvcResult.getResponse().getContentAsString());
      id = result.getInt("id");
    }
    @Test
    public void testPreuzimiPB() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/fanAdmin/"+id+"/pb"))
                .andReturn();
        JSONArray jsonArr0 = new JSONArray(mvcResult.getResponse().getContentAsString());
        Assert.assertEquals(0, jsonArr0.length());
    }
//radi ako ostane isti StartData
    @Test
    public void testPreuzimiZaOdobrenje() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/fanAdmin/odobrenje"))
                .andReturn();
        JSONObject result = new JSONObject( mvcResult.getResponse().getContentAsString());
        Assert.assertNotEquals("", result);
    }
}
