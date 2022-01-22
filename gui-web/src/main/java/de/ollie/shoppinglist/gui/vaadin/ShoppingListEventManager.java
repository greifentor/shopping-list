package de.ollie.shoppinglist.gui.vaadin;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Value;

public class ShoppingListEventManager {

	private static final Logger LOGGER = LogManager.getLogger(ShoppingListEventManager.class);

	public enum ShoppingListEventType {
		ITEM,
		SHOP;
	}

	@Value
	public static class ShoppingListEvent {

		private ShoppingListEventType type;

	}

	public interface ShoppingListEventListener {

		void shoppingListEventDetected(ShoppingListEvent event);

	}

	private List<ShoppingListEventListener> listeners = new ArrayList<>();

	public void addShoppingListEventListener(ShoppingListEventListener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	public void fireShoppingListEvent(ShoppingListEvent event) {
		listeners.forEach(listener -> {
			try {
				listener.shoppingListEventDetected(event);
			} catch (Exception e) {
				LOGGER.warn("error detected while processing shopping list event: " + e.getMessage());
			}
		});
	}

	public void removeShoppingListEventListener(ShoppingListEventListener listener) {
		if (listener != null) {
			listeners.remove(listener);
		}
	}

}