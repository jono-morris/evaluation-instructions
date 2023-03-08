package org.openapitools.api;

import javax.annotation.Generated;

import org.openapitools.model.Company;
import org.openapitools.model.Error;
import org.openapitools.repository.CompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-08T15:37:35.034+13:00[Pacific/Auckland]")
@Controller
@RequestMapping("${openapi.mWNZCompanies.base-path:/v1}")
public class CompaniesApiController {

	private final CompaniesRepository companiesRepository;

	@Autowired
	public CompaniesApiController(CompaniesRepository companiesRepository) {
		this.companiesRepository = companiesRepository;
	}

	/**
	 * GET /companies/{id}
	 *
	 * @param id Company ID (required)
	 * @return OK (status code 200) or Not Found (status code 404)
	 */
	@Operation(operationId = "companiesIdGet", tags = { "Companies" }, responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Company.class)) }),
			@ApiResponse(responseCode = "404", description = "Not Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)) }) })
	@RequestMapping(method = RequestMethod.GET, value = "/companies/{id}", produces = { "application/json" })
	public ResponseEntity<Company> companiesIdGet(
			@Parameter(name = "id", description = "Company ID", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id) {

		Company company = companiesRepository.findById(id);
		return new ResponseEntity<>(company, HttpStatus.OK);
	}
	
}
