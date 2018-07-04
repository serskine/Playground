package com.soupthatisthick.playground.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PlaygroundAuthenticationToken implements Authentication {

	private boolean auth = true;
	private final Object credentials;
	private final Object details;
	private final Object principal;
	private final String name;

	public PlaygroundAuthenticationToken() {
		this("credentials", "details", "principal", "name");
	}

	public PlaygroundAuthenticationToken(
			final Object credentials,
			final Object details,
			final Object principal,
			final String name
	) {
		this.credentials = credentials;
		this.details = details;
		this.principal = principal;
		this.name = name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getDetails() {
		return details;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		return auth;   // By default we are authenticated
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.auth = isAuthenticated;
	}

	@Override
	public String getName() {
		return name;
	}}
