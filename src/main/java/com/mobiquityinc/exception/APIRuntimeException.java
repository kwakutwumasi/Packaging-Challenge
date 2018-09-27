package com.mobiquityinc.exception;

public class APIRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8531773668510873691L;

	public APIRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
