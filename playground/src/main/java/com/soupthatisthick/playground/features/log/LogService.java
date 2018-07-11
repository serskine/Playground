package com.soupthatisthick.playground.features.log;

import com.soupthatisthick.playground.features.base.BaseRequest;
import com.soupthatisthick.playground.features.base.BaseService;
import com.soupthatisthick.playground.features.log.model.ListLogsRequest;
import com.soupthatisthick.playground.features.log.model.LogEntity;
import com.soupthatisthick.playground.features.log.model.LogEntry;
import com.soupthatisthick.playground.features.log.model.PurgeLogsRequest;
import com.soupthatisthick.playground.util.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class LogService extends BaseService {

	@Autowired
	private LogRepository logRepository;

	@Transactional
	public LogEntry create(
		@NotNull String endpoint,
		@NotNull RequestMethod method,
		@NotNull BaseRequest request
	) {
		LogEntity logEntity = new LogEntity();

		logEntity.setEndpoint(endpoint);
		logEntity.setMethod(method);
		logEntity.setRequest(JsonUtil.toJson(request));

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
