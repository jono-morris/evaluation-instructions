package nz.co.mwnz.service;

import nz.co.mwnz.model.Company;

/**
 * A Service for accessing {@code Company} details.
 */
public interface CompaniesService {

	/**
	 * Finds a company in the repository using the provided id.
	 * 
	 * @param id the id of the company to find
	 * @return a {@link Company} retrieved from the repository
	 */
	public Company findById(int id);
}
