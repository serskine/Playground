package com.soupthatisthick.playground.features.log.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.soupthatisthick.playground.features.base.BaseResponse;

import java.net.URL;
import java.time.LocalDateTime;

public class LogEntry extends BaseResponse<LogEntity> {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale = "en_CA", timezone = "AST")
	private final LocalDateTime created;

	private final String requestedUrl;

	private final Integer remotePort;
	private final String remoteHost;
	private final String remoteAddress;
	private final String remoteUser;

	private final JsonNode request;

	public LogEntry(LogEntity logEntity) {
		super(logEntity);
		// About us
		this.created = logEntity.getCreated();
		this.requestedUrl = logEntity.getRequestedUrl();
		// About them
		this.remoteAddress = logEntity.getRemoteAddress();
		this.remoteHost = logEntity.getRemoteHost();
		this.remotePort = logEntity.getRemotePort();
		this.remoteUser = logEntity.getRemoteUser();
		// The request details
		this.request = logEntity.getRequestAsJsonNode();
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public JsonNode getRequest() {
		return request;
	}
}
