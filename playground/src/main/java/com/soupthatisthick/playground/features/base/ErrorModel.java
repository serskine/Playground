package com.soupthatisthick.playground.features.base;

import com.soupthatisthick.playground.util.exception.AppException;
import org.springframework.http.HttpStatus;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public class ErrorModel extends BaseResponse<Throwable> {

	@NotNull
	private final String className;

	@Nullable
	private final String message;

	@NotNull
	private final HttpStatus status;

	@Nullable
	private final StackTraceElement stackTraceElement;

	@Nullable
	private final ErrorModel cause;


	public ErrorModel(@NotNull final AppException e) {
		super(e);

		this.status = e.getStatusCode();
		this.message = e.getMessage();
		this.className = e.getClass().getCanonicalName();
		this.stackTraceElement = e.getStackTrace()[0];

		if (e.getCause() != null) {
			this.cause = new ErrorModel(e.getCause());
		} else {
			this.cause = null;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public ErrorModel(@NotNull final Throwable e) {
		super(e);

		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
		this.message = e.getMessage();
		this.className = e.getClass().getCanonicalName();
		this.stackTraceElement = e.getStackTrace()[1];

		if (e.getCause() != null) {
			this.cause = new ErrorModel(e.getCause());
		} else {
			this.cause = null;
		}
	}

	// ---------------------------------------------------- //


	public HttpStatus getStatus() {
		return status;
	}

	@Nullable
	public String getMessage() {
		return message;
	}

	@Nullable
	public ErrorModel getCause() {
		return cause;
	}

	public String getClassName() {
		return className;
	}

	@Nullable
	public StackTraceElement getStackTraceElement() {
		return stackTraceElement;
	}
}
