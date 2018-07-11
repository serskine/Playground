package com.soupthatisthick.playground.features.log.model;

import com.soupthatisthick.playground.util.logger.Logger;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

public class SimpleHttpServletRequestSnapshot {
	private RequestMethod method;
	private URL url;
	private Map<String, String> headerMap;

	public SimpleHttpServletRequestSnapshot(HttpServletRequest request) {
		this.method = RequestMethod.valueOf(request.getMethod());
		try {
			String urlAsString = request.getRequestURL().toString();
			this.url = new URL(urlAsString);
		} catch (MalformedURLException e) {
			Logger.LOG.warning("Failed to determine URL from " + HttpServletRequest.class.getSimpleName() + ".");
			this.url = null;
		}
		this.headerMap = new TreeMap<>();
		copyHeaders(request);
	}

	private void copyHeaders(final HttpServletRequest request) {
		this.headerMap.clear();

		Enumeration<String> requestHeaderNames = request.getHeaderNames();

		while(requestHeaderNames.hasMoreElements()) {
			final String headerName = requestHeaderNames.nextElement();
			final String headerValue = request.getHeader(headerName);

			this.headerMap.put(headerName, headerValue);
		}
	}


	// ---------------------------------------------------- //


	public RequestMethod getMethod() {
		return method;
	}

	public URL getUrl() {
		return url;
	}

	public Map<String, String> getHeaders() {
		return this.headerMap;
	}

}
