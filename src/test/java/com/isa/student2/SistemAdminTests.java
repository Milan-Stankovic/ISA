package com.isa.student2;

import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ISA.IsaApplication;
import com.isa.ISA.DTO.AdminDTO;
import com.isa.ISA.DTO.PozoristeBioskopDTO;
import com.isa.ISA.dbModel.BodovnaSkala;
import com.isa.ISA.dbModel.enums.TipAdmina;
import com.isa.ISA.dbModel.enums.TipUstanove;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = IsaApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class SistemAdminTests {
	@Autowired
    MockMvc mockMvc;
	
	private ObjectMapper jsonMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private static int id = 1; 

    
    @Test
    public void testPreuzimiAdmine() throws Exception {
    	AdminDTO adm = new AdminDTO();
        adm.setEmail("adm100@a.com");
        adm.setPass("default");
        adm.setPozBio(new long[0]);
        adm.setTipAdmina(TipAdmina.SYS);
        adm.setUsername("adm100");

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/admins")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(adm)))
                .andReturn();
        JSONObject result = new JSONObject( mvcResult.getResponse().getContentAsString());
        id = result.getInt("id");
        
        MvcResult mvcResult1 = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/admins"))
                .andReturn();
        JSONArray jsonArr = new JSONArray(mvcResult1.getResponse().getContentAsString());
        Assert.assertNotEquals(0, jsonArr.length());
    }
    @Test
    public void testGetAdmin() throws Exception {
    	AdminDTO adm = new AdminDTO();
        adm.setEmail("adm10@a.com");
        adm.setPass("default");
        adm.setPozBio(new long[0]);
        adm.setTipAdmina(TipAdmina.SYS);
        adm.setUsername("adm10");

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/admins")
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(adm)))
                .andReturn();
        MvcResult mvcResult2 = mockMvc.perform(
                MockMvcRequestBuilders.get("/admin/adm10"))
                .andReturn();
        JSONObject jsonObj = new JSONObject(mvcResult2.getResponse().getContentAsString());
        Assert.assertEquals("adm10@a.com", jsonObj.getString("email"));
    }
//radi post, ne radi get
    @Test
    public void testPostBodovi() throws Exception {
    	BodovnaSkala bs = new BodovnaSkala();
    	bs.setBronzePopust(10);
    	bs.setBronzeTreshold(100);
    	bs.setGoldPopust(25);
    	bs.setGoldTreshold(500);
    	bs.setSilverPopust(15);
    	bs.setSilverTreshold(250);
    //post
    	 MvcResult mvcResult = mockMvc.perform(
                 MockMvcRequestBuilders.post("/bodSkala")
                         .contentType(contentType)
                         .content(jsonMapper.writeValueAsString(bs)))
                 .andReturn();
         JSONObject jsonObj = new JSONObject( mvcResult.getResponse().getContentAsString()); 
         Assert.assertEquals(25, jsonObj.getInt("goldPopust"));
    }

    
    @Test
    public void testPostPB() throws Exception {
    	PozoristeBioskopDTO pb = new PozoristeBioskopDTO();
    	pb.setAdresa("adresa");
    	pb.setUrlMape("");
    	pb.setTip(TipUstanove.BIOSKOP);
    	pb.setOpis("test-opis");
    	pb.setNaziv("test");
    
    	 MvcResult mvcResult = mockMvc.perform(
                 MockMvcRequestBuilders.post("/pbDTO")
                         .contentType(contentType)
                         .content(jsonMapper.writeValueAsString(pb)))
                 .andReturn();
         JSONObject jsonObj = new JSONObject( mvcResult.getResponse().getContentAsString());    
         Assert.assertEquals("test", jsonObj.getString("naziv"));
    }
}
