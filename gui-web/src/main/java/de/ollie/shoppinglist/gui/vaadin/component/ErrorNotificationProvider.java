package de.ollie.shoppinglist.gui.vaadin.component;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ErrorNotificationProvider {

	public static void open(String message, String buttonToolTip) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		notification.setPosition(Position.MIDDLE);
		Label labelMessage = new Label(message);
		com.vaadin.flow.component.button.Button closeButton =
				new com.vaadin.flow.component.button.Button(new Icon(VaadinIcon.CLOSE_SMALL));
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		closeButton.getElement().setAttribute("aria-label", buttonToolTip);
		closeButton.addClickListener(event -> notification.close());
		HorizontalLayout layout = new HorizontalLayout(labelMessage, closeButton);
		layout.setAlignItems(Alignment.CENTER);
		notification.add(layout);
		notification.open();
	}

}