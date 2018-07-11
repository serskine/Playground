package com.soupthatisthick.playground.features.log.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.soupthatisthick.playground.features.base.BaseRequest;
import com.soupthatisthick.playground.util.json.JsonUtil;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.DispatcherType;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * This is meant to be a snapshot of an HttpServletRequest
 */
public class HttpServletRequestSnapshot extends BaseRequest {

	private final String pathInfo;
	private final String pathTranslated;
	private final String pathContext;
	private final RequestMethod method;
	private final String authType;
	private final Cookie[] cookies;

	private final String queryString;
	private final String remoteUser;
	private final String userPrincipal;
	private final String requestedSessionId;
	private final DispatcherType dispatcherType;

	private final boolean isAsyncSupported;

	private final int localPort;
	private final String localAddress;
	private final String localName;
	private final int remotePort;
	private final boolean isSecure;

	private final Collection<Locale> locales;

	private final Locale locale;
	private final String remoteHost;
	private final String remoteAddress;
	private final int serverPort;

	private final Map<String, String> headerMap;

	private final String requestUri;
	private final StringBuffer requestUrl;
	private final String servletPath;

	private final boolean isRequestedSessionIdValid;
	private final boolean isRequestedSessionIdFromCookie;
	private final boolean isRequestedSessionIdFromURL;

	private final Map<String, JsonNode> attributeMap;

	private final String characterEncoding;
	private final long contentLength;
	private final String contentType;

	private final Map<String, String[]> parameterMap;

	private final String protocol;
	private final String scheme;
	private final String serverName;

	// -------------------------------------------------------- //

	/**
	 * Creates a snapshot of the HttpServletRequest. The body will not be contained in the servlet
	 * because it can only be read once from it's input stream.
	 * @param request is the {@link HttpServletRequest} model to copy data from.
	 */
	public HttpServletRequestSnapshot(final HttpServletRequest request) {


		this.protocol = request.getProtocol();
		this.scheme = request.getScheme();
		this.serverName = request.getServerName();

		this.parameterMap = new TreeMap<>();
		copyParameters(request);

		this.contentType = request.getContentType();
		this.contentLength = request.getContentLengthLong();
		this.characterEncoding = request.getCharacterEncoding();

		this.attributeMap = new TreeMap<>();
		copyAttributes(request);

		this.isRequestedSessionIdFromCookie = request.isRequestedSessionIdFromCookie();
		this.isRequestedSessionIdFromURL = request.isRequestedSessionIdFromURL();
		this.isRequestedSessionIdValid = request.isRequestedSessionIdValid();

		this.servletPath = request.getServletPath();
		this.requestUrl = request.getRequestURL();
		this.requestUri = request.getRequestURI();

		this.headerMap = new TreeMap<>();
		copyHeaders(request);

		this.serverPort = request.getServerPort();
		this.remoteAddress = request.getRemoteAddr();
		this.remoteHost = request.getRemoteHost();
		this.locale = request.getLocale();

		this.locales = new HashSet<>();
		copyLocales(request);

		this.isSecure = request.isSecure();
		this.remotePort = request.getRemotePort();
		this.localName = request.getLocalName();
		this.localAddress = request.getLocalAddr();
		this.localPort = request.getLocalPort();

		this.isAsyncSupported = request.isAsyncSupported();

		this.dispatcherType = request.getDispatcherType();
		this.requestedSessionId = request.getRequestedSessionId();
		this.userPrincipal = (request.getUserPrincipal()==null) ? null : request.getUserPrincipal().toString();
		this.remoteUser = request.getRemoteUser();
		this.queryString = request.getQueryString();

		this.cookies = request.getCookies();

		this.authType = request.getAuthType();
		this.method = RequestMethod.valueOf(request.getMethod());
		this.pathContext = request.getContextPath();
		this.pathTranslated = request.getPathTranslated();
		this.pathInfo = request.getPathInfo();

	}

	private void copyLocales(final HttpServletRequest request) {
		this.locales.clear();
		Enumeration<Locale> requestLocales = request.getLocales();
		while(requestLocales.hasMoreElements()) {
			final Locale requestLocale = requestLocales.nextElement();
			this.locales.add(requestLocale);
		}
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

	private void copyAttributes(final HttpServletRequest request) {
		this.attributeMap.clear();

		Enumeration<String> requestAttributeNames = request.getAttributeNames();

		while(requestAttributeNames.hasMoreElements()) {
			final String attributeName = requestAttributeNames.nextElement();
			final Object attributeValue = request.getAttribute(attributeName);
			final JsonNode attributeNode = JsonUtil.toJsonNode(attributeValue);

			this.attributeMap.put(attributeName, attributeNode);
		}
	}

	private void copyParameters(final HttpServletRequest request) {
		this.parameterMap.clear();

		final Map<String, String[]> requestMap = request.getParameterMap();
		for(String parameterName : requestMap.keySet()) {
			this.parameterMap.put(parameterName, requestMap.get(parameterName));
		}
	}

	public String getPathInfo() {
		return pathInfo;
	}

	public String getPathTranslated() {
		return pathTranslated;
	}

	public String getPathContext() {
		return pathContext;
	}

	public RequestMethod getMethod() {
		return method;
	}

	public String getAuthType() {
		return authType;
	}

	public Cookie[] getCookies() {
		return cookies;
	}

	public String getQueryString() {
		return queryString;
	}

	public String getRemoteUser() {
		return remoteUser;
	}

	public String getUserPrincipal() {
		return userPrincipal;
	}

	public String getRequestedSessionId() {
		return requestedSessionId;
	}

	public DispatcherType getDispatcherType() {
		return dispatcherType;
	}

	public boolean isAsyncSupported() {
		return isAsyncSupported;
	}

	public int getLocalPort() {
		return localPort;
	}

	public String getLocalAddress() {
		return localAddress;
	}

	public String getLocalName() {
		return localName;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public boolean isSecure() {
		return isSecure;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public StringBuffer getRequestUrl() {
		return requestUrl;
	}

	public String getServletPath() {
		return servletPath;
	}

	public boolean isRequestedSessionIdValid() {
		return isRequestedSessionIdValid;
	}

	public boolean isRequestedSessionIdFromCookie() {
		return isRequestedSessionIdFromCookie;
	}

	public boolean isRequestedSessionIdFromURL() {
		return isRequestedSessionIdFromURL;
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public long getContentLength() {
		return contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getScheme() {
		return scheme;
	}

	public String getServerName() {
		return serverName;
	}

	public Collection<Locale> getLocales() {
		return this.locales;
	}

	public Map<String, String> getHeaderMap() {
		return this.headerMap;
	}

	public Map<String, JsonNode> getAttributeMap() {
		return this.attributeMap;
	}

	public Map<String, String[]> getParameterMap() {
		return this.parameterMap;
	}

	// -------------------------------------------------------- //

}