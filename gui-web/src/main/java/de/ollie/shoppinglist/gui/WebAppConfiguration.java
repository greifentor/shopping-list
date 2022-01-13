package de.ollie.shoppinglist.gui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class WebAppConfiguration {

	@Value("${app.version}")
	private String appVersion;

	@Value("${cube.url}")
	private String cubeURL;

}