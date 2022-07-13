package com.greenfox.treasuryauctionsystem.controllers;

import com.greenfox.treasuryauctionsystem.services.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {


	@Autowired
	private MockMvc mockMvc;


	@Test
	void shouldCreateMockMvc() {
		assertNotNull(mockMvc);
	}

	@Test
	void helloController() throws Exception {
		//Unauthorized because of spring security
		mockMvc.perform(MockMvcRequestBuilders.get("/hello")).andExpect(status().isFound());
		//mockMvc.perform(MockMvcRequestBuilders.get("/hello")).andExpect(status().isUnauthorized());
		//mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().is2xxSuccessful());
	}

}
