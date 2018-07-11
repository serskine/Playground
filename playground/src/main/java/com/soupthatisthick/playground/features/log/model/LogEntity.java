package com.soupthatisthick.playground.features.log.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.soupthatisthick.playground.features.base.BaseEntity;
import com.soupthatisthick.playground.util.json.JsonUtil;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.jemos.podam.common.PodamExclude;

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

	@Column(name="requestedUrl", nullable = false)
	@URL
	private String requestedUrl;

	@Column(name="remoteHost")
	private String remoteHost;

	@Column(name="remoteUser")
	private String remoteUser;

	@Column(name="remotePort")
	private Integer remotePort;

	@Column(name="remoteAddress")
	private String remoteAddress;


	@Column(name="request")
	@Length(max=32767)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
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

	public RequestMethod getMethod() {
		return method;
	}

	public void setMethod(RequestMethod method) {
		this.method = method;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public JsonNode getRequestAsJsonNode() {
		return JsonUtil.toJsonNodeFromJsonString(request);
	}

	public void setRequestAsJsonNode(JsonNode jsonNode) {
		this.request = jsonNode.asText();
	}

	public String getRequestedUrl() {
		return requestedUrl;
	}

	public void setRequestedUrl(String requestedUrl) {
		this.requestedUrl = requestedUrl;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public String getRemoteUser() {
		return remoteUser;
	}

	public void setRemoteUser(String remoteUrl) {
		this.remoteUser = remoteUrl;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public Integer getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(Integer remotePort) {
		this.remotePort = remotePort;
	}
}
