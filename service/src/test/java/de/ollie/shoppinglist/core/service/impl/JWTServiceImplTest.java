package de.ollie.shoppinglist.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;

import de.ollie.shoppinglist.core.model.User;
import de.ollie.shoppinglist.core.service.JWTService.AuthorizationData;
import de.ollie.shoppinglist.core.service.UserService;
import de.ollie.shoppinglist.core.service.exception.JWTNotValidException;

@ExtendWith(MockitoExtension.class)
class JWTServiceImplTest {

	private static final String SECRET = "the-secret";
	private static final int BASE_VALIDITY_IN_MINUTES = 5;

	private static final String RIGHT_1 = "right1";
	private static final String RIGHT_2 = "right2";

	private static final String APPLICATION_NAME = "application-name";
	private static final String[] APPLICATION_RIGHTS = new String[] { RIGHT_1, RIGHT_2 };
	private static final LocalDateTime END_OF_VALIDITY =
			LocalDateTime.now().plusMinutes(BASE_VALIDITY_IN_MINUTES).withNano(0);
	private static final String USER_GLOBAL_ID = "TEST-USER";
	private static final String USER_NAME = "T.User";
	private static final String USER_TOKEN = "tu";

	private static final User USER = new User().setGlobalId(USER_GLOBAL_ID).setName(USER_NAME).setToken(USER_TOKEN);

	@Mock
	private JWTServiceConfiguration configuration;
	@Mock
	private UserService userService;

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
				// Prepare
				when(configuration.getSecret()).thenReturn(SECRET);
				// Run & Check
				assertThrows(
						JWTNotValidException.class,
						() -> unitUnderTest
								.getAuthorizationData(createJWT(USER_NAME, null, END_OF_VALIDITY, APPLICATION_RIGHTS)));
			}

			@Test
			void passAJWTWithoutAnyApplicationRights_throwsAnException() {
				// Prepare
				when(configuration.getSecret()).thenReturn(SECRET);
				// Run & Check
				assertThrows(
						JWTNotValidException.class,
						() -> unitUnderTest
								.getAuthorizationData(createJWT(USER_NAME, APPLICATION_NAME, END_OF_VALIDITY, null)));
			}

			@Test
			void passAJWTWithoutAnEndOfValidity_throwsAnException() {
				// Prepare
				when(configuration.getSecret()).thenReturn(SECRET);
				// Run & Check
				assertThrows(
						JWTNotValidException.class,
						() -> unitUnderTest
								.getAuthorizationData(
										createJWT(USER_NAME, APPLICATION_NAME, null, APPLICATION_RIGHTS)));
			}

			@Test
			void passAJWTWithoutAUserName_throwsAnException() {
				// Prepare
				when(configuration.getSecret()).thenReturn(SECRET);
				// Run & Check
				assertThrows(
						JWTNotValidException.class,
						() -> unitUnderTest
								.getAuthorizationData(
										createJWT(null, APPLICATION_NAME, END_OF_VALIDITY, APPLICATION_RIGHTS)));
			}

			@Test
			void passAJWTUnmatchingSecret_throwsAnException() {
				// Prepare
				when(configuration.getSecret()).thenReturn(SECRET);
				// Run & Check
				assertThrows(
						SignatureVerificationException.class,
						() -> unitUnderTest
								.getAuthorizationData(
										createJWT(
												USER_NAME,
												APPLICATION_NAME,
												END_OF_VALIDITY,
												APPLICATION_RIGHTS,
												SECRET + ";op")));
			}

			@Test
			void passAJWTWithTooLongValidity_throwsAnException() {
				// Prepare
				when(configuration.getBaseValidityInMinutes()).thenReturn(BASE_VALIDITY_IN_MINUTES);
				when(configuration.getSecret()).thenReturn(SECRET);
				// Run & Check
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
						new AuthorizationData(APPLICATION_NAME, END_OF_VALIDITY, USER, APPLICATION_RIGHTS);
				String jwt = createFullyLoadedJWT();
				when(configuration.getBaseValidityInMinutes()).thenReturn(BASE_VALIDITY_IN_MINUTES);
				when(configuration.getSecret()).thenReturn(SECRET);
				when(userService.findByGlobalId(USER_GLOBAL_ID)).thenReturn(Optional.of(USER));
				// Run
				AuthorizationData returned = unitUnderTest.getAuthorizationData(jwt);
				// Check
				assertEquals(expected, returned);
			}

		}

	}

	@Nested
	class TestsOfMethod_getEndOfvalidity_String {

		@Test
		void passAJWTWithoutAnEndOfValidity_throwsAnException() {
			// Prepare
			when(configuration.getSecret()).thenReturn(SECRET);
			// Run & Check
			assertEquals(
					END_OF_VALIDITY,
					unitUnderTest
							.getEndOfValidity(
									createJWT(USER_NAME, APPLICATION_NAME, END_OF_VALIDITY, APPLICATION_RIGHTS)));
		}

	}

	private static String createFullyLoadedJWT() {
		return createJWT(USER_NAME, APPLICATION_NAME, END_OF_VALIDITY, APPLICATION_RIGHTS);
	}

	private static String createJWT(String userName, String applicationName, LocalDateTime endOfValidity,
			String[] applicationRights, String... secrets) {
		Algorithm algorithm = Algorithm.HMAC512(secrets.length > 0 ? secrets[0] : SECRET);
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
		builder.withClaim("userGlobalId", USER_GLOBAL_ID);
		builder.withClaim("userToken", USER_TOKEN);
		return builder.sign(algorithm);
	}

}