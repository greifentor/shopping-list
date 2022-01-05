package de.ollie.shoppinglist.core.service.impl;

import static de.ollie.shoppinglist.util.Check.ensure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Named;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import de.ollie.shoppinglist.core.service.JWTService;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

	@Override
	public AuthorizationData getAuthorizationData(String jwt) {;
		ensure(jwt != null, "jwt cannot be null.");
		DecodedJWT decodedJWT = JWT.decode(jwt);
		return new AuthorizationData(
				decodedJWT.getClaims().get("userName").asString(),
				getLocalDateTime(decodedJWT.getClaims().get("endOfValidity").asString()),
				decodedJWT.getClaims().get("applicationRights").asArray(String.class));
	}

	private LocalDateTime getLocalDateTime(String s) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(s, formatter);
	}

}