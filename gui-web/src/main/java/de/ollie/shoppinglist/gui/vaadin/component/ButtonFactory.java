package de.ollie.shoppinglist.gui.vaadin.component;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;

import de.ollie.shoppinglist.core.service.localization.ResourceManager;
import de.ollie.shoppinglist.gui.SessionData;
import de.ollie.shoppinglist.gui.vaadin.ApplicationStartLayout;

/**
 * A factory for buttons.
 *
 * @author ollie (16.08.2021) (overtook from carp-core)
 */
public class ButtonFactory {

	public static Button createButton(String text) {
		return createButton(text, null);
	}

	public static Button createButton(String text,
			ComponentEventListener<ClickEvent<com.vaadin.flow.component.button.Button>> action) {
		Button button = new Button(text)
				.setBackgroundColor("gainsboro")
				.setBorder("solid 2px")
				.setBorderColor("#c0c0c0")
				.setColor("black");
		if (action != null) {
			button.addClickListener(action);
		}
		return button;
	}

	public static Button createAddButton(ResourceManager resourceManager, Consumer<ClickEvent<?>> action,
			SessionData sessionData) {
		return createResourcedButton(resourceManager, "commons.button.add.text", action, sessionData);
	}

	public static Button createBackButton(ResourceManager resourceManager, Supplier<Optional<UI>> uiSupplier,
			String urlBack, SessionData sessionData) {
		Button buttonBack = ButtonFactory
				.createButton(
						resourceManager.getLocalizedString("commons.button.back.text", sessionData.getLocalization()));
		buttonBack.addClickListener(event -> uiSupplier.get().ifPresent(ui -> ui.navigate(urlBack)));
		return buttonBack;
	}

	public static Button createCancelButton(ResourceManager resourceManager, Consumer<ClickEvent<?>> action,
			SessionData sessionData) {
		return createResourcedButton(resourceManager, "commons.button.cancel.text", action, sessionData);
	}

	public static Button createEditButton(ResourceManager resourceManager, Consumer<ClickEvent<?>> action,
			SessionData sessionData) {
		return createResourcedButton(resourceManager, "commons.button.edit.text", action, sessionData);
	}

	public static Button createLogoutButton(ResourceManager resourceManager, Supplier<Optional<UI>> uiSupplier,
			SessionData sessionData, Logger logger) {
		Button buttonLogout = ButtonFactory
				.createButton(
						resourceManager
								.getLocalizedString("commons.button.logout.text", sessionData.getLocalization()));
		buttonLogout.addClickListener(event -> uiSupplier.get().ifPresent(ui -> {
			logger.info("user '{}' logged out.", sessionData.getAuthorizationData().getUser().getName());
			sessionData.setAuthorizationData(null);
			ui.navigate(ApplicationStartLayout.URL);
		}));
		return buttonLogout;
	}

	public static Button createOkButton(ResourceManager resourceManager, Consumer<ClickEvent<?>> action,
			SessionData sessionData) {
		return createResourcedButton(resourceManager, "commons.button.ok.text", action, sessionData);
	}

	public static Button createRemoveButton(ResourceManager resourceManager, Consumer<ClickEvent<?>> action,
			SessionData sessionData) {
		return createResourcedButton(resourceManager, "commons.button.remove.text", action, sessionData);
	}

	private static Button createResourcedButton(ResourceManager resourceManager, String resourceId,
			Consumer<ClickEvent<?>> action, SessionData sessionData) {
		Button button = ButtonFactory
				.createButton(resourceManager.getLocalizedString(resourceId, sessionData.getLocalization()));
		button.addClickListener(action::accept);
		return button;
	}

	public static Button createSaveButton(ResourceManager resourceManager, Consumer<ClickEvent<?>> action,
			SessionData sessionData) {
		return createResourcedButton(resourceManager, "commons.button.save.text", action, sessionData);
	}

}