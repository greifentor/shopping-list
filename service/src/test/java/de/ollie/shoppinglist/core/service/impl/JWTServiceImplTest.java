package de.ollie.shoppinglist.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.shoppinglist.core.service.JWTService.AuthorizationData;

@ExtendWith(MockitoExtension.class)
public class JWTServiceImplTest {

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

		}

		@Nested
		class CleanRun {

			@Test
			void passAValidJWT_returnsACorrectAuthorizationData() {
				// Prepare
				AuthorizationData expected =
						new AuthorizationData(
								"test-user",
								LocalDateTime.of(2022, 12, 31, 23, 59, 59),
								new String[] { "right1", "right2" });
				String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhcHBsaWNhdGlvblJpZ2h0cyI6WyJyaWdodDEiLCJyaWdodDIiXSwiZW5kT2ZWYWxpZGl0eSI6IjIwMjItMTItMzEgMjM6NTk6NTkiLCJ1c2VyTmFtZSI6InRlc3QtdXNlciIsImFwcGxpY2F0aW9uTmFtZSI6InRlc3QtYXBwIn0.03HRqO4ddslay0352U3ZmCYI8MamgCFoLDUhp_fAUFoBC4VkH6GdF-zLvPi10gQnjtEZWxqQHDvKY3no1JNNbg";
				// Run
				AuthorizationData returned = unitUnderTest.getAuthorizationData(jwt);
				// Check
				assertEquals(expected, returned);
			}

		}
	}

}