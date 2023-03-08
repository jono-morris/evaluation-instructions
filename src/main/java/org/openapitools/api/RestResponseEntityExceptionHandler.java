package org.openapitools.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global error handling component for exceptions thrown during operation of the API.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles {@link ResourceNotFoundException} thrown when a resource could not be found in the remote system.
	 * @param ex the exception to handle
	 * @return Not Found (status code 404)
	 */
	@ExceptionHandler(value = { ResourceNotFoundException.class })
	protected ResponseEntity<Object> handleNotFoundException(RuntimeException ex) {
		
		org.openapitools.model.Error error = new org.openapitools.model.Error().error("not found").errorDescription(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}