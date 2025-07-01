package com.booking.theater.exceptions;

public class ThreaterNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ThreaterNotFoundException() {
		super();
	}
	
	public ThreaterNotFoundException(String message) {
		super(message);
	}
}
