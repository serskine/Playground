package com.soupthatisthick.playground.features.log.model;

import com.soupthatisthick.playground.features.log.LogService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import static com.soupthatisthick.playground.util.logger.Logger.LOG;

@Component
public class LogRequestInterceptor extends HandlerInterceptorAdapter {

	private final LogService logService;

	public LogRequestInterceptor(@NotNull final LogService logService) {
		this.logService = logService;
	}

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) {
		try {
			if (logService != null) {
				logService.create(request);
			} else {
				LOG.warning("logService is null");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return true;
	}

	@Override
	public void postHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler,
		ModelAndView modelAndView
	) {
	}

	@Override
	public void afterCompletion(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler,
		Exception ex
	) {
	}
}
