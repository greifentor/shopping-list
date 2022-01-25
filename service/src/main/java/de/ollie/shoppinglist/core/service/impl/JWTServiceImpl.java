package de.ollie.shoppinglist.core.service.impl;

import static de.ollie.shoppinglist.util.Check.ensure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import de.ollie.shoppinglist.core.model.User;
import de.ollie.shoppinglist.core.service.JWTService;
import de.ollie.shoppinglist.core.service.UserService;
import de.ollie.shoppinglist.core.service.exception.JWTNotValidException;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

	private static final Logger LOGGER = LogManager.getLogger(JWTServiceImpl.class);

	private final JWTServiceConfiguration configuration;
	private final UserService userService;

	@Override
	public AuthorizationData getAuthorizationData(String jwt) {
		ensure(jwt != null, "jwt cannot be null.");
		verifyJWT(jwt);
		DecodedJWT decodedJWT = decodeJWT(jwt);
		ensureJWTContainsDataForAllField(decodedJWT);
		ensureEndOfValidityIsInRange(decodedJWT);
		return createAuthorizationData(decodedJWT);
	}

	private void verifyJWT(String jwt) {
		Algorithm algorithm = Algorithm.HMAC512(configuration.getSecret());
		JWTVerifier verifier = JWT.require(algorithm).build();
		verifier.verify(jwt);
	}

	private DecodedJWT decodeJWT(String jwt) {
		return JWT.decode(jwt);
	}

	private void ensureJWTContainsDataForAllField(DecodedJWT decodedJWT) {
		ensure(decodedJWT.getClaims().get("applicationName") != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get("applicationRights") != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get("endOfValidity") != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get("userGlobalId") != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get("userName") != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get("userToken") != null, new JWTNotValidException());
	}

	private void ensureEndOfValidityIsInRange(DecodedJWT decodedJWT) {
		if (!configuration.isTestMode()) {
			LocalDateTime endOfValidity = getLocalDateTime(decodedJWT.getClaims().get("endOfValidity").asString());
			ensure(
					!endOfValidity.minusMinutes(configuration.getBaseValidityInMinutes()).isAfter(LocalDateTime.now()),
					new JWTNotValidException());
		} else {
			LOGGER.info("token end of validtity ignored by test mode!");
		}
	}

	private LocalDateTime getLocalDateTime(String s) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(s, formatter);
	}

	private AuthorizationData createAuthorizationData(DecodedJWT decodedJWT) {
		LocalDateTime endOfValidity = getLocalDateTime(decodedJWT.getClaims().get("endOfValidity").asString());
		return new AuthorizationData(
				decodedJWT.getClaims().get("applicationName").asString(),
				endOfValidity,
				findByGlobalIdOrCreate(decodedJWT),
				decodedJWT.getClaims().get("applicationRights").asArray(String.class));
	}

	private User findByGlobalIdOrCreate(DecodedJWT decodedJWT) {
		return userService
				.findByGlobalId(decodedJWT.getClaims().get("userGlobalId").asString())
				.orElseGet(() -> createUser(decodedJWT));
	}

	private User createUser(DecodedJWT decodedJWT) {
		return userService
				.update(
						userService
								.create(
										new User()
												.setGlobalId(decodedJWT.getClaims().get("userGlobalId").asString())
												.setName(decodedJWT.getClaims().get("userName").asString())
												.setToken(decodedJWT.getClaims().get("userToken").asString())));
	}

	@Override
	public LocalDateTime getEndOfValidity(String jwt) {
		return getLocalDateTime(decodeJWT(jwt).getClaims().get("endOfValidity").asString());
	}

}