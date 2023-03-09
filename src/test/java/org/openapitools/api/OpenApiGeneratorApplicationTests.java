package org.openapitools.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;


/**
 * These tests start and operate on a running system, suitable for integration testing.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class OpenApiGeneratorApplicationTests extends TestBase {

	/**	Assigns a random port to start the server on. */
	@Value(value = "${local.server.port}")
	private int port;

	/** The rest-template automatically provided by Spring Boot.  */
	@Autowired
	private TestRestTemplate restTemplate;

	/** Verify the JSON response when making an 'awesome' company request. */
	@Test
	public void awesomeCompanyResponse() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/v1/companies/" + AWESOME_COMPANY_ID,
				String.class)).isEqualTo("{\"id\":1,\"name\":\"MWNZ\",\"description\":\"..is awesome\"}");
	}

	/** Verify the JSON response when making a request for the 'not so awesome' company. */
	@Test
	public void lessAwesomeCompanyResponse() throws Exception {
		assertThat(this.restTemplate
				.getForObject("http://localhost:" + port + "/v1/companies/" + NOT_SO_ASWSOME_COMPANY_ID, String.class))
				.isEqualTo("{\"id\":2,\"name\":\"Other\",\"description\":\"....is not\"}");
	}
	
	/** Verify the JSON response when making a request for a company that has no entry in the remote system. */
	@Test
	public void errorResponse() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/v1/companies/" + NO_RESOURCE_FOR_COMPANY_ID, String.class))
		.isEqualTo("{\"error\":\"not found\",\"error_description\":\"Could not access resource for id 3\"}");
	}
}