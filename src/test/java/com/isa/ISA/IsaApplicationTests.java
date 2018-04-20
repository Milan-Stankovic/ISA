package com.isa.ISA;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ISA.DTO.RegKorDTO;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
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

import java.nio.charset.Charset;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = IsaApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class IsaApplicationTests {

	@Autowired
	MockMvc mockMvc;

	private RegKorDTO loginUser;
	@Before
	public void setup(){
		loginUser = new RegKorDTO();
		loginUser.setPassword("f");
		loginUser.setUserName("f");
	}
	private ObjectMapper jsonMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
	private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	@Test
	public void contextLoads() throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/login")
						.contentType(contentType)
				.content(jsonMapper.writeValueAsString(loginUser)))
				.andReturn();
		String l = mvcResult.getResponse().getContentAsString();
		System.out.println(l);
		ObjectMapper mapper = new ObjectMapper();
/*
		List<Poziv> actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Poziv>>() {});*/
		System.out.println(l);
		Assert.assertTrue(l.contains("\"userName\":\"f\""));

	}

}
