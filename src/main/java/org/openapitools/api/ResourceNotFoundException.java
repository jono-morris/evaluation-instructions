package org.openapitools.api;

/**
 * Exception thrown when a requested resource cannot be found in a system.
 */
public class ResourceNotFoundException extends RuntimeException {

	/** The serial version for this exception. */
	private static final long serialVersionUID = 8479401851848242708L;

	/**
	 * Constructs a new exception with a detail message.
	 * 
	 * @param message the detail message. The detail message is saved for later
	 *                retrieval.
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
