package com.soupthatisthick.playground.features.base;

import com.soupthatisthick.playground.features.log.LogService;
import com.soupthatisthick.playground.util.exception.AppException;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

import static com.soupthatisthick.playground.util.logger.Logger.LOG;

public class BaseController {

	@Autowired
	protected LogService logService;

	@ExceptionHandler(AppException.class)
	@ResponseBody
	public ErrorModel catchApiException(final AppException apiException, final HttpServletResponse httpResponse) {
		LOG.error("An unexpected error occurred. " + apiException.getMessage(), apiException);

		ErrorModel error = new ErrorModel(apiException);
		httpResponse.setStatus(error.getStatus().value());
		return error;
	}

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public ErrorModel catchAll(final Throwable throwable, final HttpServletResponse httpResponse) {
		LOG.error("An unexpected error occurred. " + throwable.getMessage(), throwable);

		ErrorModel error = new ErrorModel(throwable);
		httpResponse.setStatus(error.getStatus().value());
		return error;
	}
}
