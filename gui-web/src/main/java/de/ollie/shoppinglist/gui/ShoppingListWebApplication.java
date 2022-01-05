package de.ollie.shoppinglist.gui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The entry point of the Spring Boot application.
 * 
 * @author ollie (05.01.2022)
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("de.ollie.shoppinglist.persistence.repository")
@ComponentScan("de.ollie")
@EntityScan("de.ollie.shoppinglist.persistence.entity")
public class ShoppingListWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingListWebApplication.class, args);
	}

}
