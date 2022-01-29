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
		ensure(decodedJWT.getClaims().get(CLAIM_NAME_APPLICATION_NAME) != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get(CLAIM_NAME_APPLICATION_RIGHTS) != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get(CLAIM_NAME_LOGIN_DATE) != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get(CLAIM_NAME_USER_GLOBAL_ID) != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get(CLAIM_NAME_USER_NAME) != null, new JWTNotValidException());
		ensure(decodedJWT.getClaims().get(CLAIM_NAME_USER_TOKEN) != null, new JWTNotValidException());
	}

	private void ensureEndOfValidityIsInRange(DecodedJWT decodedJWT) {
		if (!configuration.isTestMode()) {
			LocalDateTime loginDate = getLoginDate(decodedJWT);
			ensure(
					!loginDate.minusMinutes(configuration.getBaseValidityInMinutes()).isAfter(LocalDateTime.now()),
					new JWTNotValidException());
		} else {
			LOGGER.info("token end of validtity ignored by test mode!");
		}
	}

	private LocalDateTime getLoginDate(DecodedJWT decodedJWT) {
		return getLocalDateTime(decodedJWT.getClaims().get(CLAIM_NAME_LOGIN_DATE).asString());
	}

	private LocalDateTime getLocalDateTime(String s) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(s, formatter);
	}

	private AuthorizationData createAuthorizationData(DecodedJWT decodedJWT) {
		LocalDateTime loginDate = getLoginDate(decodedJWT);
		return new AuthorizationData(
				decodedJWT.getClaims().get(CLAIM_NAME_APPLICATION_NAME).asString(),
				loginDate,
				findByGlobalIdOrCreate(decodedJWT),
				decodedJWT.getClaims().get(CLAIM_NAME_APPLICATION_RIGHTS).asArray(String.class));
	}

	private User findByGlobalIdOrCreate(DecodedJWT decodedJWT) {
		return userService
				.findByGlobalId(getUserGlobalId(decodedJWT))
				.orElseGet(() -> createUser(decodedJWT));
	}

	private String getUserGlobalId(DecodedJWT decodedJWT) {
		return decodedJWT.getClaims().get(CLAIM_NAME_USER_GLOBAL_ID).asString();
	}

	private User createUser(DecodedJWT decodedJWT) {
		return userService
				.update(
						userService
								.create(
										new User()
												.setGlobalId(getUserGlobalId(decodedJWT))
												.setName(decodedJWT.getClaims().get(CLAIM_NAME_USER_NAME).asString())
												.setToken(
														decodedJWT.getClaims().get(CLAIM_NAME_USER_TOKEN).asString())));
	}

	@Override
	public LocalDateTime getLoginDate(String jwt) {
		return getLocalDateTime(decodeJWT(jwt).getClaims().get(CLAIM_NAME_LOGIN_DATE).asString());
	}

}