package edu.avans.hartigehap.integrationTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;
import edu.avans.hartigehap.web.controller.rs.CustomerRS;
import edu.avans.hartigehap.web.controller.rs.body.LoginRequest;
import edu.avans.hartigehap.web.controller.rs.testutil.RestTestUtil;

/**
 * This integration test will start on the controller layer and goes through the
 * whole application, down to the database.
 * 
 */
public class LoginTest extends AbstractTransactionRollbackTest {

	@Autowired
	private CustomerRS customerRS;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = standaloneSetup(customerRS).build();
	}

	@Test
	public void successfulManagerLoginTest() throws Exception {
		String managerEmail = "manager@hh.nl";
		String managerPassword = "manager";
		
		// Manager login
		LoginRequest loginRequest = new LoginRequest();
		
		// Following is automatically created in populator
		loginRequest.setEmail(managerEmail);
		loginRequest.setPassword(managerPassword);

		ResultActions result = callLogin(loginRequest);
		
		result.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data").isNotEmpty())
			.andExpect(jsonPath("$.data.email").value(managerEmail));
	}

	@Test
	public void unsuccessfulCustomerLoginTest() throws Exception {
		// Customer login
		LoginRequest loginRequest = new LoginRequest();
		
		// Email address exists but password is invalid
		loginRequest.setEmail("peterlimonade@gmail.com");
		loginRequest.setPassword("incorrectpassword");

		ResultActions result = callLogin(loginRequest);
		
		result.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.success").value(false))
			.andExpect(jsonPath("$.data").isNotEmpty())
			.andExpect(jsonPath("$.data").value("login_fail"));
	}

	private ResultActions callLogin(LoginRequest request) throws Exception {
		return mockMvc.perform(post("/rest/v1/login").contentType(RestTestUtil.APPLICATION_JSON_UTF8)
				.content(RestTestUtil.convertObjectToJSONContent(request)));
	}
}
