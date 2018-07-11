package com.soupthatisthick.playground.features.setting.model;

import com.soupthatisthick.playground.features.base.BaseRequest;

import javax.annotation.Nullable;

public class SettingRequest extends BaseRequest {

	@Nullable
	private String name;

	// ---------------------------------------------------- //

	@Nullable
	public String getName() {
		return name;
	}

	public void setName(@Nullable String name) {
		this.name = name;
	}
}