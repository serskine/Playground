package com.soupthatisthick.playground.features.log;

import com.soupthatisthick.playground.features.base.BaseController;
import com.soupthatisthick.playground.features.log.model.ListLogsRequest;
import com.soupthatisthick.playground.features.log.model.LogEntry;
import com.soupthatisthick.playground.features.log.model.PurgeLogsRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(description = "Service Request Logs")
@RestController
@RequestMapping(value = "/logs", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogController extends BaseController {

	@ApiOperation(value = "List requests recorded from this service. This request is recorded in the logs.")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public List<LogEntry> listLogs(@NotNull @Valid ListLogsRequest request) {
		logService.create("/logs/list", RequestMethod.POST, request);
		return logService.list(request);
	}

	@ApiOperation(value = "Purge log entries. This request is recorded in the logs.")
	@RequestMapping(value="/purge", method = RequestMethod.DELETE)
	public void purgeLogs(@NotNull @Valid PurgeLogsRequest request) {
		logService.create("/logs/purge", RequestMethod.DELETE, request);
		logService.purgeBefore(request);
	}
}
