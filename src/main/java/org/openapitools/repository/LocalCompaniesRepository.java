package org.openapitools.repository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.openapitools.model.Company;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;

//@Repository
public class LocalCompaniesRepository implements CompaniesRepository {

	private static final String COMPANIES_RESOURCE_PATTERN = "classpath:/companies/*.xml";

	private static Map<Integer, Company> companies = new HashMap<>();

	static {
		
		try {
			List<File> resources = getResourceUrls(COMPANIES_RESOURCE_PATTERN);
			JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
			for (File resource : resources) {
				Data data = (Data) unmarshaller.unmarshal(resource);
				
				Company company = new Company().name(data.getName()).description(data.getDescription())
						.id(Integer.parseInt(data.getId()));

				companies.put(company.getId(), company);
			}

		} catch (IOException | JAXBException e) {
			e.printStackTrace();
		}

	}

	public LocalCompaniesRepository() {

	}

	public Company findById(Integer id) {
		return companies.get(id);
	}

	private static List<File> getResourceUrls(String locationPattern) throws IOException {
		ClassLoader classLoader = LocalCompaniesRepository.class.getClassLoader();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
		Resource[] resources = resolver.getResources(locationPattern);

		return Arrays.stream(resources).map(LocalCompaniesRepository::toFile).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	private static File toFile(Resource r) {
		try {
			if (r == null) {
				return null;
			}
			return r.getFile();
		} catch (IOException e) {
			return null;
		}
	}
}
