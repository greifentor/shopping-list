package de.ollie.shoppinglist.gui.vaadin;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ollie.shoppinglist.core.model.Item;
import de.ollie.shoppinglist.core.model.ListPosition;
import de.ollie.shoppinglist.core.model.Shop;
import lombok.Value;

public class ShoppingListEventManager {

	private static final Logger LOGGER = LogManager.getLogger(ShoppingListEventManager.class);

	public enum ShoppingListEventType {
		ITEM,
		LIST_POSITION,
		SHOP;
	}

	public enum ActionType {
		ADD,
		REMOVE;
	}

	public interface ShoppingListEvent<T> {

		ActionType getAction();

		ShoppingListEventType getType();

		T getValue();

	}

	@Value
	public static class ItemShoppingListEvent implements ShoppingListEvent<Item> {

		private ActionType action;
		private ShoppingListEventType type = ShoppingListEventType.ITEM;
		private Item value;

	}

	@Value
	public static class ListPositionShoppingListEvent implements ShoppingListEvent<ListPosition> {

		private ActionType action;
		private ShoppingListEventType type = ShoppingListEventType.LIST_POSITION;
		private ListPosition value;

	}

	@Value
	public static class ShopShoppingListEvent implements ShoppingListEvent<Shop> {

		private ActionType action;
		private ShoppingListEventType type = ShoppingListEventType.SHOP;
		private Shop value;

	}

	public interface ShoppingListEventListener {

		void shoppingListEventDetected(ShoppingListEvent<?> event);

	}

	private List<ShoppingListEventListener> listeners = new ArrayList<>();

	public void addShoppingListEventListener(ShoppingListEventListener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	public void fireShoppingListEvent(ShoppingListEvent<?> event) {
		for (int i = listeners.size() - 1; i >= 0; i--) {
			try {
				listeners.get(i).shoppingListEventDetected(event);
			} catch (Exception e) {
				LOGGER.warn("error detected while processing shopping list event: " + e.getMessage());
			}
		}
	}

	public void removeShoppingListEventListener(ShoppingListEventListener listener) {
		if (listener != null) {
			listeners.remove(listener);
		}
	}

}