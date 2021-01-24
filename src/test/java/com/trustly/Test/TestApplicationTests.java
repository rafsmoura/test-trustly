package com.trustly.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.containsString;

import static org.hamcrest.Matchers.containsInAnyOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustly.comunication.GitHubRepositoryRequest;

@SpringBootTest
@AutoConfigureMockMvc
class TestApplicationTests {
	
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void verifyUrl() {
		
		GitHubRepositoryRequest repo = new GitHubRepositoryRequest();
		repo.setUrl("http://google.com.br");
		
		try {
			mockMvc.perform(post("/api/trustly/get-info")
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(repo)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.errors", hasSize(1)))
			.andExpect(jsonPath("$.errors[*].message", containsInAnyOrder(repo.getUrl() + " is not a valid GitHub repository URL."))		
			);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
			
	}
	
	@Test
	public void checkRepository() {
		
		GitHubRepositoryRequest repo = new GitHubRepositoryRequest();
		repo.setUrl("https://github.com/rafsmoura/StormTest");
		
		try {
			mockMvc.perform(post("/api/trustly/get-info")
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(repo)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", containsString("StormTest")))
			.andExpect(jsonPath("$.url", containsString("https://github.com/rafsmoura/StormTest"))
			);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
