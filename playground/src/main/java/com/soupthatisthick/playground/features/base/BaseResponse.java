package com.soupthatisthick.playground.features.base;

import javax.validation.constraints.NotNull;

public class BaseResponse<Entity> extends BaseModel {
	public BaseResponse(@NotNull Entity entity) {

	}
}
