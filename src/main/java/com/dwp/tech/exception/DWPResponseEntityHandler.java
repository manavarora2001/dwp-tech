package com.dwp.tech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * ResponseEntityHandler for the  application. Handles the various application exceptions
 * 
 *
 */
@ControllerAdvice
@RestController
public class DWPResponseEntityHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * Throws exception for HTTP status code 500 - Internal Server Error
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request){
		ExceptionResponse response =  new ExceptionResponse(ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Throws exception for HTTP status code 400 - Bad Request
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(InvalidRequest.class)
	public final ResponseEntity<Object> handleInvalidRequestException(Exception ex, WebRequest request){
		ExceptionResponse response =  new ExceptionResponse(ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
