package com.gotgam.bansi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class BansiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mvc;
	
	@Test
	void helloTest() throws Exception {
		mvc.perform(get("/accounts/v1/hello"))	
			.andExpect(status().isOk())
			.andExpect(content().string("hello"));
	}
}
