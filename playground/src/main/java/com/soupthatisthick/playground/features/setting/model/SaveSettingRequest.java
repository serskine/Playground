package com.soupthatisthick.playground.features.setting.model;

import com.soupthatisthick.playground.features.base.BaseRequest;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public class SaveSettingRequest extends BaseRequest {

	@NotNull
	private String name;

	@Nullable
	private String value;

	// ---------------------------------------------------- //

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
