package com.soupthatisthick.playground.util.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
	private final HttpStatus statusCode;

	public AppException(final String message) {
		this(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
	}

	public AppException(final String message, final Throwable cause) {
		this(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
	}

	public AppException(final HttpStatus statusCode, final String message, final Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}
}
