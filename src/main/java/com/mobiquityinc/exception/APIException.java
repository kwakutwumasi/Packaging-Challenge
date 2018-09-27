package com.mobiquityinc.exception;

/**Exception thrown when there is an error parsing the input file
 * @author Kwaku Twumasi-Afriyie <kwaku.twumasi@quakearts.com>
 *
 */
public class APIException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5199629890017767754L;

	public APIException(String message) {
		super(message);
	}

	public APIException(String message, Throwable cause) {
		super(message, cause);
	}
		
}
