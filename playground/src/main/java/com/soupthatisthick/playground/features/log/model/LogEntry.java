package com.soupthatisthick.playground.features.log.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soupthatisthick.playground.features.base.BaseResponse;

import java.time.LocalDateTime;

import static com.soupthatisthick.playground.util.logger.Logger.LOG;

public class LogEntry extends BaseResponse<LogEntity> {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale = "en_CA", timezone = "AST")
	private final LocalDateTime created;

	private final String endpoint;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private final String request;

	public LogEntry(LogEntity logEntity) {
		super(logEntity);
		this.created = logEntity.getCreated();
		this.endpoint = logEntity.getEndpoint();
		this.request = logEntity.getRequest();
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public String getRequest() {
		return request;
	}
}
