package com.soupthatisthick.playground.features.log;

import com.soupthatisthick.playground.features.base.BaseController;
import com.soupthatisthick.playground.features.log.model.ListLogsRequest;
import com.soupthatisthick.playground.features.log.model.LogEntry;
import com.soupthatisthick.playground.features.log.model.PurgeLogsRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.RequestContent;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(description = "Service Request Logs")
@RestController
@RequestMapping(value = "/logs", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LogController extends BaseController {

	@ApiOperation(value = "List requests recorded from this service. This request is recorded in the logs.")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public List<LogEntry> listLogs(@RequestBody @Valid ListLogsRequest request) {
		return logService.list(request);
	}

	@ApiOperation(value = "Purge log entries. This request is recorded in the logs.")
	@RequestMapping(value="/purge", method = RequestMethod.DELETE)
	public void purgeLogs(@RequestBody @Valid PurgeLogsRequest request) {
		logService.purgeBefore(request);
	}
}
