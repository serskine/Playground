package com.soupthatisthick.playground.features.log;

import com.soupthatisthick.playground.features.base.BaseRequest;
import com.soupthatisthick.playground.features.base.BaseService;
import com.soupthatisthick.playground.features.log.model.*;
import com.soupthatisthick.playground.util.json.JsonUtil;
import com.soupthatisthick.playground.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class LogService extends BaseService {

	@Autowired
	private LogRepository logRepository;

	@Transactional
	public LogEntry create(@NotNull HttpServletRequest request) throws MalformedURLException {
		final SimpleHttpServletRequestSnapshot snapshotRequest = new SimpleHttpServletRequestSnapshot(request);

		LogEntity logEntity = new LogEntity();


		logEntity.setMethod(snapshotRequest.getMethod());

		logEntity.setRequestedUrl(request.getRequestURL().toString());

		logEntity.setRemoteUser(request.getRemoteUser());
		logEntity.setRemoteAddress(request.getRemoteAddr());
		logEntity.setRemoteHost(request.getRemoteHost());
		logEntity.setRemotePort(request.getRemotePort());

		final String asJson = JsonUtil.toJson(snapshotRequest, true, true);

		Logger.LOG.debug("\n{}", asJson);

		logEntity.setRequest(asJson);

		return new LogEntry(logRepository.save(logEntity));
	}

	/**
	 * This will delete logs before the provided cutoff date
	 * @param request will provide the required details
	 */
	@Transactional
	public void purgeBefore(@NotNull PurgeLogsRequest request) {
		if (request.getCutoff()!=null) {
			logRepository.purgeBefore(request.getCutoff());
		} else {
			logRepository.deleteAllInBatch();
		}
	}

	/**
	 * This will return a list of logs.
	 * @param request will contain the details for the time period we want logs for
	 * @return a {@link List<LogEntity>} containing all pertinent log entries
	 */
	@Transactional
	public List<LogEntry> list(@NotNull ListLogsRequest request) {
		if (request.getStartTimeInclusive()==null) {
			if (request.getEndTimeExclusive()==null) {
				return logRepository.findAllLogs(request.asPageable());
			} else {
				return logRepository.findBeforeExclusive(request.getEndTimeExclusive(), request.asPageable());
			}
		} else {
			if (request.getEndTimeExclusive() == null) {
				return logRepository.findAfterInclusive(request.getStartTimeInclusive(), request.asPageable());
			} else {
				return logRepository.findBetween(
						request.getStartTimeInclusive(),
						request.getEndTimeExclusive(),
						request.asPageable()
				);
			}
		}
	}
}
