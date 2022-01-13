package de.ollie.shoppinglist.core.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

/**
 * @author ollie (06.01.2022)
 */
@Configuration
@Getter
public class JWTServiceConfiguration {

	@Value("${jwt.service.secret}")
	private String secret;
	@Value("${jwt.service.base-validity-in-minutes:5}")
	private int baseValidityInMinutes;
	@Value("${test.mode:false}")
	private boolean testMode;

}