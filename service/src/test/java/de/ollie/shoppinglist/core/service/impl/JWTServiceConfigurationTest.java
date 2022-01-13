package de.ollie.shoppinglist.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JWTServiceConfigurationTest {

	@Inject
	private JWTServiceConfiguration unitUnderTest;

	@Test
	void returnsTheCorrectValueFor_baseValidityInMinutes() {
		assertEquals(10, unitUnderTest.getBaseValidityInMinutes());
	}

	@Test
	void returnsTheCorrectValueFor_secret() {
		assertEquals("the-secret", unitUnderTest.getSecret());
	}

}