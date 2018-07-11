package com.soupthatisthick.playground.features.setting.model;

import com.soupthatisthick.playground.features.base.BaseModel;
import com.soupthatisthick.playground.features.base.BaseResponse;
import com.soupthatisthick.playground.features.log.model.LogEntity;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import static com.soupthatisthick.playground.util.logger.Logger.LOG;

public class Setting extends BaseResponse<SettingEntity> {

	@NotNull
	private String name;

	@Nullable
	private String value;

	// ---------------------------------------------------- //

	public Setting(SettingEntity settingEntity) {
		super(settingEntity);
		this.name = settingEntity.getName();
		this.value = settingEntity.getValue();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Nullable
	public String getValue() {
		return value;
	}

	public void setValue(@Nullable String value) {
		this.value = value;
	}

}
