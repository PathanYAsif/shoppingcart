package com.adp.exception;

/**
 * Custom exception to report/log application errors which are not recoverable.
 * The exception is intentionally a runtime exception to avoid forcing clients
 * to handle exceptions which cannot be recovered from.
 * 
 * @author Asif Pathan
 *
 */
@SuppressWarnings("serial")
public class ShoppingCartException extends RuntimeException {

	public ShoppingCartException() {
		super();
	}

	public ShoppingCartException(String message) {
		super(message);
	}

	public ShoppingCartException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShoppingCartException(Throwable cause) {
		super(cause);
	}

	protected ShoppingCartException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
