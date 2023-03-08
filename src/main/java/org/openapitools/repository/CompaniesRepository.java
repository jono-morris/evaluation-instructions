package org.openapitools.repository;

import org.openapitools.model.Company;

/**
 * A Repository that hold Company details.
 */
public interface CompaniesRepository {

	/**
	 * Finds a company in the repository using the provided id.
	 * @param id the id of the company to find
	 * @return a {@link Company} object created using details retrieved from the repository
	 */
	public Company findById(Integer id);
}