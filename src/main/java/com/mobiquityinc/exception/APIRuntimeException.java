package com.mobiquityinc.exception;

/**Exception thrown when there is an unrecoverable error during processing.
 * @author Kwaku Twumasi-Afriyie <kwaku.twumasi@quakearts.com>
 *
 */public class APIRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8531773668510873691L;

	public APIRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
