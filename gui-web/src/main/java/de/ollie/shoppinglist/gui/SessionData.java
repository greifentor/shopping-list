package de.ollie.shoppinglist.gui;

import org.springframework.stereotype.Component;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import de.ollie.shoppinglist.core.model.localization.LocalizationSO;
import de.ollie.shoppinglist.core.service.JWTService.AuthorizationData;
import de.ollie.shoppinglist.gui.vaadin.go.SessionIdGO;
import lombok.Data;

/**
 * An object to hold data during the session.
 *
 * @author ollie (07.01.2022)
 */
@Component
@VaadinSessionScope
@Data
public class SessionData {

	private static int counter = 0;

	private AuthorizationData authorizationData;
	private SessionIdGO id = new SessionIdGO("SHOPPING-LIST-SESSION-" + (counter++));
	private LocalizationSO localization = LocalizationSO.DE;

}