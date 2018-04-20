package com.isa.ISA;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
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
	private RegKorDTO registerUser;
	@Before
	public void setup(){
		registerUser = new RegKorDTO();
		registerUser.setUserName("test");
		registerUser.setPassword("test");
		registerUser.setIme("test");
		registerUser.setPrezime("test");
		registerUser.setGrad("test");
		registerUser.setBrojTelefona("test");
		registerUser.setEmail("tanja_indjic@yahoo.com");

		loginUser = new RegKorDTO();
		loginUser.setPassword(registerUser.getPassword());
		loginUser.setUserName(registerUser.getUserName());

	}
	private ObjectMapper jsonMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
	private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	@Test
	public void login1() throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/register")
						.contentType(contentType)
						.content(jsonMapper.writeValueAsString(registerUser)))
				.andReturn();
		String l = mvcResult.getResponse().getContentAsString();
		System.out.println("REGISTER:");
		System.out.println(l);
		Assert.assertEquals("", l);
	}

	@Test
	public void login2() throws Exception {
		//ODGOVOR NA MAIL
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/register/" + registerUser.getEmail())).andReturn();

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/login")
						.contentType(contentType)
				.content(jsonMapper.writeValueAsString(loginUser)))
				.andReturn();
		String l = mvcResult.getResponse().getContentAsString();
		System.out.println("LOGIN1:");
		System.out.println(l);
/*		ObjectMapper mapper = new ObjectMapper();
		List<Poziv> actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Poziv>>() {});*/

		//TREBA DA VRATI KORISNIKA SAD
		Assert.assertTrue(l.contains("\"userName\":\"test\""));

	}





}
