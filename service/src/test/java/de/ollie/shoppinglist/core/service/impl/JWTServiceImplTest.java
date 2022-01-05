package de.ollie.shoppinglist.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;

import de.ollie.shoppinglist.core.service.JWTService.AuthorizationData;
import de.ollie.shoppinglist.core.service.exception.JWTNotValidException;

@ExtendWith(MockitoExtension.class)
public class JWTServiceImplTest {

	private static final String RIGHT_1 = "right1";
	private static final String RIGHT_2 = "right2";

	private static final String APPLICATION_NAME = "application-name";
	private static final String[] APPLICATION_RIGHTS = new String[] { RIGHT_1, RIGHT_2 };
	private static final LocalDateTime END_OF_VALIDITY =
			LocalDateTime.now().plusMinutes(JWTServiceImpl.BASE_VALIDITY_IN_MINUTES).withNano(0);
	private static final String USER_NAME = "test-user";

	@InjectMocks
	private JWTServiceImpl unitUnderTest;

	@Nested
	class TestsOfMethod_getAuthorizationData_String {

		@Nested
		class ExceptionalBehavior {

			@Test
			void passANullValue_throwsAnException() {
				assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getAuthorizationData(null));
			}

			@Test
			void passAJWTWithoutAnApplicationName_throwsAnException() {
				assertThrows(
						JWTNotValidException.class,
						() -> unitUnderTest
								.getAuthorizationData(createJWT(USER_NAME, null, END_OF_VALIDITY, APPLICATION_RIGHTS)));
			}

			@Test
			void passAJWTWithoutAnyApplicationRights_throwsAnException() {
				assertThrows(
						JWTNotValidException.class,
						() -> unitUnderTest
								.getAuthorizationData(createJWT(USER_NAME, APPLICATION_NAME, END_OF_VALIDITY, null)));
			}

			@Test
			void passAJWTWithoutAnEndOfValidity_throwsAnException() {
				assertThrows(
						JWTNotValidException.class,
						() -> unitUnderTest
								.getAuthorizationData(
										createJWT(USER_NAME, APPLICATION_NAME, null, APPLICATION_RIGHTS)));
			}

			@Test
			void passAJWTWithoutAUserName_throwsAnException() {
				assertThrows(
						JWTNotValidException.class,
						() -> unitUnderTest
								.getAuthorizationData(
										createJWT(null, APPLICATION_NAME, END_OF_VALIDITY, APPLICATION_RIGHTS)));
			}

			@Test
			void passAJWTUnmatchingSecret_throwsAnException() {
				assertThrows(
						SignatureVerificationException.class,
						() -> unitUnderTest
								.getAuthorizationData(
										createJWT(
												USER_NAME,
												APPLICATION_NAME,
												END_OF_VALIDITY,
												APPLICATION_RIGHTS,
												JWTServiceImpl.SECRET + ";op")));
			}

			@Test
			void passAJWTWithTooLongValidity_throwsAnException() {
				assertThrows(
						JWTNotValidException.class,
						() -> unitUnderTest
								.getAuthorizationData(
										createJWT(
												USER_NAME,
												APPLICATION_NAME,
												END_OF_VALIDITY.plusMinutes(1),
												APPLICATION_RIGHTS)));
			}

		}

		@Nested
		class CleanRun {

			@Test
			void passAValidJWT_returnsACorrectAuthorizationData() {
				// Prepare
				AuthorizationData expected =
						new AuthorizationData(USER_NAME, APPLICATION_NAME, END_OF_VALIDITY, APPLICATION_RIGHTS);
				String jwt = createFullyLoadedJWT();
				// Run
				AuthorizationData returned = unitUnderTest.getAuthorizationData(jwt);
				// Check
				assertEquals(expected, returned);
			}

		}

	}

	private static String createFullyLoadedJWT() {
		return createJWT(USER_NAME, APPLICATION_NAME, END_OF_VALIDITY, APPLICATION_RIGHTS);
	}

	private static String createJWT(String userName, String applicationName, LocalDateTime endOfValidity,
			String[] applicationRights, String... secrets) {
		Algorithm algorithm = Algorithm.HMAC512(secrets.length > 0 ? secrets[0] : JWTServiceImpl.SECRET);
		Builder builder = JWT.create();
		if (applicationName != null) {
			builder.withClaim("applicationName", applicationName);
		}
		if (applicationRights != null) {
			builder.withArrayClaim("applicationRights", applicationRights);
		}
		if (endOfValidity != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			builder.withClaim("endOfValidity", endOfValidity.format(formatter));
		}
		if (userName != null) {
			builder.withClaim("userName", userName);
		}
		return builder.sign(algorithm);
	}

}