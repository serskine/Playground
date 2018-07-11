package com.soupthatisthick.playground.features.setting.model;

import com.soupthatisthick.playground.features.base.BasePageRequest;

import javax.annotation.Nullable;

public class ListSettingsRequest extends BasePageRequest {

	@Nullable
	private String searchTerm;

	// ---------------------------------------------------- //

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
}
