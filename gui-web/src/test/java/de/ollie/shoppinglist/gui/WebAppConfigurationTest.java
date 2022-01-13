package de.ollie.shoppinglist.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WebAppConfigurationTest {

	@Inject
	private WebAppConfiguration unitUnderTest;

	@Test
	void appVersion_isSet() {
		assertFalse(unitUnderTest.getAppVersion().isEmpty());
	}

	@Test
	void returnsTheCorrectValueFor_cubeURL() {
		assertEquals("http://localhost:8080/cube/menu", unitUnderTest.getCubeURL());
	}

}