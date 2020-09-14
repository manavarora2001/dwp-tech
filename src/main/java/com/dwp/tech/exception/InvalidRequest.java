package com.dwp.tech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception raised for the Internal Server error Status code 400
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequest extends RuntimeException {
	
	public InvalidRequest(String message) {
		super(message);
	}
}
