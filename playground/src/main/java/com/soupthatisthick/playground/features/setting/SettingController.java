package com.soupthatisthick.playground.features.setting;

import com.soupthatisthick.playground.features.base.BaseController;
import com.soupthatisthick.playground.features.setting.model.ListSettingsRequest;
import com.soupthatisthick.playground.features.setting.model.SettingRequest;
import com.soupthatisthick.playground.features.setting.model.SaveSettingRequest;
import com.soupthatisthick.playground.features.setting.model.Setting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(description = "Settings Service")
@RestController
@RequestMapping(value = "/setting", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class SettingController extends BaseController {

	@Autowired
	private SettingService settingService;

	@ApiOperation(value = "Sets the value of a desired setting")
	@RequestMapping(value="/save", method = RequestMethod.PUT)
	public void save(@RequestBody @Valid SaveSettingRequest request) {
		settingService.save(request);
	}

	@ApiOperation(value = "Deletes the desired setting")
	@RequestMapping(value="/delete", method = RequestMethod.DELETE)
	public void delete(@RequestBody @Valid SettingRequest request) {
		settingService.delete(request.getName());
	}

	@ApiOperation(value = "Reads the setting with the given name")
	@RequestMapping(value="/read", method = RequestMethod.POST)
	public Setting read(@RequestBody @Valid SettingRequest request) {
		return settingService.read(request.getName());
	}

	@ApiOperation(value = "Lists settings that have names beginning with the specified terms.")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public List<Setting> list(@RequestBody @Valid ListSettingsRequest request) {
		return settingService.list(request);
	}


	// ---------------------------------------------------- //
}
