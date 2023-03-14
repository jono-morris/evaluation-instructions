package org.openapitools.repository;

import nz.co.mwnz.model.Company;

/**
 * A Repository for accessing {@code Company} details.
 */
public interface CompaniesRepository {

	/**
	 * Finds a company in the repository using the provided id.
	 * 
	 * @param id the id of the company to find
	 * @return a {@link Company} retrieved from the repository
	 */
	public Company findById(Integer id);
}
