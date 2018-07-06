package com.soupthatisthick.playground.features.base;

import com.soupthatisthick.playground.features.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
	@Autowired
	protected LogService logService;
}
