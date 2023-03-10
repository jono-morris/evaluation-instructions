package org.openapitools.repository;

import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.openapitools.api.ResourceNotFoundException;
import org.openapitools.model.Company;
import org.springframework.stereotype.Repository;

/**
 * A repository that retrieves company details from XML files held in github.
 */
@Repository
public class RemoteCompaniesRepository implements CompaniesRepository {

	/** Template for company resources held on the remote system. */
	private static final String XML_API = "https://raw.githubusercontent.com/MiddlewareNewZealand/evaluation-instructions/main/xml-api/%s.xml";

	private static final JAXBContext jaxbContext;

	static {
		try {
			jaxbContext = JAXBContext.newInstance(CompanyData.class);
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Company findById(Integer id) {

		CompanyData companyData = null;
		try {
			URL url = new URL(String.format(XML_API, id));
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			companyData = (CompanyData) unmarshaller.unmarshal(url);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Could not access resource for id " + id);
		}

		return new Company().name(companyData.getName()).description(companyData.getDescription())
				.id(Integer.parseInt(companyData.getId()));
	}
}
