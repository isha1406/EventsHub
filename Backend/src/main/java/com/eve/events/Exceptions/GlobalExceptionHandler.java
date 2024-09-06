package com.eve.events.Exceptions;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

		/*if Id not found in service Controller then 
	orElseThrow method throw here it will give response*/
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public String HandelResourcerNotFoundException(ResourceNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public String HandelBadCredentialsException(BadCredentialsException ex) {
		return "Invalid Username Or Password Entered !!!";
	}
}