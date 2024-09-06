package com.eve.events.Exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super();
	}
	
    public ResourceNotFoundException(String message) {
		super(message);
		log.error("Resource not found: {}", message);
	}

}
