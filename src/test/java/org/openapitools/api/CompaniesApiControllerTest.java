package org.openapitools.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.Company;
import org.openapitools.repository.RemoteCompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Unit tests for {@link CompaniesApiController} where Spring Boot instantiates
 * only the web layer rather than the whole context.
 */
@WebMvcTest(CompaniesApiController.class)
public class CompaniesApiControllerTest {

	/** The id of the awesome company held in the remote system. */
	private static final int AWESOME_COMPANY_ID = 1;

	/** The id of the not so awesome company held in the remote system. */
	private static final int NOT_SO_ASWSOME_COMPANY_ID = 2;

	/** An id of a company not held in the remote system */
	private static final int NO_RESOURCE_FOR_COMPANY_ID = 3;
	
	@Autowired
	private MockMvc mockMvc;

	/**
	 * Mock instance of the companies repository that the
	 * {@code CompaniesApiController} will use.
	 */
	@MockBean
	private RemoteCompaniesRepository companies;

	private Company awesomeCompany() {
		return new Company().id(AWESOME_COMPANY_ID).name("MWNZ").description("..is awesome");
	};

	private Company notSoAwesomeCompany() {
		return new Company().id(NOT_SO_ASWSOME_COMPANY_ID).name("Other").description("....is not");
	};

	@BeforeEach
	void setup() {
		given(this.companies.findById(AWESOME_COMPANY_ID)).willReturn(awesomeCompany());
		given(this.companies.findById(NOT_SO_ASWSOME_COMPANY_ID)).willReturn(notSoAwesomeCompany());
		given(this.companies.findById(3)).willThrow(new ResourceNotFoundException("Could not access resource for id 3"));
	}

	/**
	 * Verify that an 'awesome company' HTTP get request is successful and returns
	 * the expected response and HTTP 200.
	 */
	@Test
	void testAwesomeCompany() throws Exception {
		mockMvc.perform(get("/v1/companies/" + AWESOME_COMPANY_ID)).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(AWESOME_COMPANY_ID)).andExpect(jsonPath("$.name").value("MWNZ"))
				.andExpect(jsonPath("$.description").value("..is awesome"));
	}

	/**
	 * Verify that a 'not so awesome company' HTTP get request is successful and
	 * returns the expected response and HTTP 200.
	 */
	@Test
	void testNotSoAwesomeCompany() throws Exception {
		mockMvc.perform(get("/v1/companies/" + NOT_SO_ASWSOME_COMPANY_ID)).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(NOT_SO_ASWSOME_COMPANY_ID))
				.andExpect(jsonPath("$.name").value("Other")).andExpect(jsonPath("$.description").value("....is not"));
	}

	/**
	 * Verify that a HTTP get request for a company that does not exist in the
	 * remote system returns a HTTP 4XX
	 */
	@Test
	void testPhantomCompany() throws Exception {
		mockMvc.perform(get("/v1/companies/" + NO_RESOURCE_FOR_COMPANY_ID)).andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.error").value("not found"))
				.andExpect(jsonPath("$.error_description").value("Could not access resource for id " + NO_RESOURCE_FOR_COMPANY_ID));
	}
}