package com.soupthatisthick.playground.security;

import com.soupthatisthick.playground.util.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class PlaygroundAuthenticationProvider implements AuthenticationProvider {

	private static transient final Logger LOG = LoggerFactory.getLogger(PlaygroundAuthenticationProvider.class);


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		LOG.warn(getClass().getSimpleName() + ": entry");

		PlaygroundAuthenticationToken authenticationToken = (PlaygroundAuthenticationToken) authentication;

		LOG.debug("Authentication token:\n{}", JsonUtil.toJson(authenticationToken, true));

		authentication.setAuthenticated(true);

		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return PlaygroundAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
