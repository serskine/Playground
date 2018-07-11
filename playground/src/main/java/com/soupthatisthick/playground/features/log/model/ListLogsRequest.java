package com.soupthatisthick.playground.features.log.model;

import com.soupthatisthick.playground.features.base.BasePageRequest;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class ListLogsRequest extends BasePageRequest {

	@Nullable
	private LocalDateTime startTimeInclusive;

	@Nullable
	private LocalDateTime endTimeExclusive;

	/**
	 * This constructor will assume we want all possible logs.
	 */
	public ListLogsRequest() {
		this(null, null);
	}

	/**
	 * This will provide a request to list logs within specified time frame.
	 * @param startTimeInclusive indicates the earliest possible log time (inclusive). If null then the oldest log will be included.
	 * @param endTimeExclusive indicates the latest possible log time (exclusive). If null then the most recent log will be included.
	 */
	public ListLogsRequest(final LocalDateTime startTimeInclusive, final LocalDateTime endTimeExclusive) {
		super();
		this.startTimeInclusive = startTimeInclusive;
		this.endTimeExclusive = endTimeExclusive;
	}

	public LocalDateTime getStartTimeInclusive() {
		return startTimeInclusive;
	}

	public void setStartTimeInclusive(LocalDateTime startTimeInclusive) {
		this.startTimeInclusive = startTimeInclusive;
	}

	public LocalDateTime getEndTimeExclusive() {
		return endTimeExclusive;
	}

	public void setEndTimeExclusive(LocalDateTime endTimeExclusive) {
		this.endTimeExclusive = endTimeExclusive;
	}
}
