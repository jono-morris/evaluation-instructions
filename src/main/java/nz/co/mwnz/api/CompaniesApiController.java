package nz.co.mwnz.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import nz.co.mwnz.model.Company;
import nz.co.mwnz.repository.CompaniesRepository;

/**
 * Controller that is responsible for handling requests to the {@code Companies}
 * API.
 */
@Controller
@RequestMapping("/v1")
public class CompaniesApiController {

	private final CompaniesRepository companiesRepository;

	/**
	 * Constructor.
	 * 
	 * @param companiesRepository the repository instance injected by the Spring Framework
	 */
	@Autowired
	public CompaniesApiController(CompaniesRepository companiesRepository) {
		this.companiesRepository = companiesRepository;
	}

	/**
	 * Method responsible for handling get requests to /v1/companies/{id}.
	 *
	 * @param id the id of the Company to get; required
	 * @return OK (status code 200) or Not Found (status code 404)
	 * @throws ResourceNotFoundException if the requested resource could not be
	 *                                   retrieved
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/companies/{id}", produces = { "application/json" })
	public ResponseEntity<Company> companiesIdGet(
			@Parameter(name = "id", description = "Company ID", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id) {

		Company company = companiesRepository.findById(id);
		if (null == company) {
			throw new ResourceNotFoundException("Could not access resource for id " + id);
		}
		return new ResponseEntity<>(company, HttpStatus.OK);
	}
}
