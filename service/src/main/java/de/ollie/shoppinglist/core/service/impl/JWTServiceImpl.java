package de.ollie.shoppinglist.core.service.impl;

import static de.ollie.shoppinglist.util.Check.ensure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Named;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import de.ollie.shoppinglist.core.service.JWTService;
import de.ollie.shoppinglist.core.service.exception.JWTNotValidException;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

	static final String SECRET = "the-secret";
	static final int BASE_VALIDITY_IN_MINUTES = 5;

	@Override
	public AuthorizationData getAuthorizationData(String jwt) {;
		ensure(jwt != null, "jwt cannot be null.");
		Algorithm algorithm = Algorithm.HMAC512(SECRET);
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(jwt);
		decodedJWT = JWT.decode(jwt);
		ensure(decodedJWT.getClaims().get("applicationName") != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get("applicationRights") != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get("endOfValidity") != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get("userName") != null, new JWTNotValidException());
		LocalDateTime endOfValidity = getLocalDateTime(decodedJWT.getClaims().get("endOfValidity").asString());
		ensure(
				!endOfValidity.minusMinutes(BASE_VALIDITY_IN_MINUTES).isAfter(LocalDateTime.now()),
				new JWTNotValidException());
		return new AuthorizationData(
				decodedJWT.getClaims().get("userName").asString(),
				decodedJWT.getClaims().get("applicationName").asString(),
				endOfValidity,
				decodedJWT.getClaims().get("applicationRights").asArray(String.class));
	}

	private LocalDateTime getLocalDateTime(String s) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(s, formatter);
	}

}