package com.soupthatisthick.playground.features.base;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BasePageRequest extends BaseRequest {

	@NotNull
	@Min(value = 0)
	@ApiModelProperty(required = true, notes = "requested page number (starts at 0)")
	private Integer pageNumber = 0;

	@NotNull
	@Min(value = 1)
	@ApiModelProperty(required = true, notes = "number of records to return")
	private Integer pageSize = Integer.MAX_VALUE;

	public Pageable asPageable() {
		return new PageRequest(pageNumber, pageSize);
	}

	public Pageable asPageable(Sort sort) {
		return new PageRequest(pageNumber, pageSize, sort);
	}

	// ------------------------------- GETTERS AND SETTERS ------------------------------- //

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}

