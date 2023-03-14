package nz.co.mwnz.repository;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import nz.co.mwnz.api.ResourceNotFoundException;
import nz.co.mwnz.model.Company;

/**
 * A repository that retrieves company details from XML files held in github.
 */
@Repository
public class CompanyRepositoryImpl implements CompaniesRepository {

	/** Template for company resources held on the remote system. */

	private static final String ROOT_URI = "https://raw.githubusercontent.com/MiddlewareNewZealand/evaluation-instructions/main/xml-api/%s.xml";

	private static final JAXBContext jaxbContext;

	static {
		try {
			jaxbContext = JAXBContext.newInstance(CompanyData.class);
		} catch (JAXBException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Company findById(int id) {	

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create(String.format(ROOT_URI, id)))
				.header("Accept", "application/xml").build();
	
		try {
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			if (response.statusCode() == HttpStatus.OK.value()) {
	
				StringReader reader = new StringReader(response.body());
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				CompanyData companyData = (CompanyData) unmarshaller.unmarshal(reader);

				return new Company().name(companyData.getName()).description(companyData.getDescription())
						.id(Integer.parseInt(companyData.getId()));
			}
		}
		catch(Exception e) {
			throw new ResourceNotFoundException("Could not access resource for id " + id);
		}
		return null;
	}
}
