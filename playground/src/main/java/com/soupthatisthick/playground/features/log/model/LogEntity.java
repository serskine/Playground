package com.soupthatisthick.playground.features.log.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soupthatisthick.playground.features.base.BaseEntity;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.jemos.podam.common.PodamExclude;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="log")
public class LogEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", updatable = false, nullable = false)
	@PodamExclude
	private Long id;

	@Override
	public int hashCode() {
		if (getId() != null) {
			return getId().hashCode();
		} else {
			return HashCodeBuilder.reflectionHashCode(this, false);
		}
	}

	@Enumerated(EnumType.STRING)
	@Column(name="method", nullable=false)
	private RequestMethod method;

	@Column(name="created", nullable=false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale = "en_CA", timezone = "AST")
	private LocalDateTime created;

	@Column(name="endpoint", nullable = false)
	private String endpoint;

	@Column(name="request")
	private String request;

	@PrePersist
	public void onPrePersistAudit() {
		created = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public RequestMethod getMethod() {
		return method;
	}

	public void setMethod(RequestMethod method) {
		this.method = method;
	}
}
