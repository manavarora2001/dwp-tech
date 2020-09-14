package com.dwp.tech.exception;

import lombok.Getter;

/**
 * Custom Exception Class for the application
 *
 *
 */

@Getter
public class ExceptionResponse {

	// description of the exception response
	private String description;

	public ExceptionResponse(String description) {
		super();
		this.description = description;

	}
}
