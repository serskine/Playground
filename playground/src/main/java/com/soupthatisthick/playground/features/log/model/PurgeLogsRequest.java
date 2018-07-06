package com.soupthatisthick.playground.features.log.model;

import com.soupthatisthick.playground.features.base.BaseRequest;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class PurgeLogsRequest extends BaseRequest {
	@Nullable
	private final LocalDateTime cutoff;

	/**
	 * This will create a request logs before the specified time.
	 * @param cutoff is the deadline (exclusive) you will delete logs until. If this is null then
	 *               all logs will be deleted.
	 */
	public PurgeLogsRequest(@Nullable LocalDateTime cutoff) {
		this.cutoff = cutoff;
	}

	public LocalDateTime getCutoff() {
		return cutoff;
	}
}
