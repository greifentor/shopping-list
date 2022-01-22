package de.ollie.shoppinglist.gui.vaadin;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.shoppinglist.core.model.Item;
import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ActionType;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ItemShoppingListEvent;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShopShoppingListEvent;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEventListener;

@ExtendWith(MockitoExtension.class)
public class ShoppingListEventManagerTest {

	private static final ItemShoppingListEvent ITEM_EVENT = new ItemShoppingListEvent(ActionType.ADD, new Item());
	private static final ShopShoppingListEvent SHOP_EVENT = new ShopShoppingListEvent(ActionType.ADD, new Shop());

	@Mock
	private ShoppingListEventListener listener;

	@InjectMocks
	private ShoppingListEventManager unitUnderTest;

	@Nested
	class TestOfMethod_addShoppingListEventListener_ShoppingListEventListener {

		@Test
		void passANullValue_noExceptionThrown() {
			unitUnderTest.addShoppingListEventListener(null);
		}

		@Test
		void passAnEventListener_listenerIsCalledDuringProcessingFiredEvents() {
			// Run
			unitUnderTest.addShoppingListEventListener(listener);
			// Check
			unitUnderTest.fireShoppingListEvent(SHOP_EVENT);
			verify(listener, times(1)).shoppingListEventDetected(SHOP_EVENT);
		}

		@Test
		void passAnEventListenerWithAndAnEventListenerWithoutException_listenerAreAllCalled() {
			// Prepare
			ShoppingListEventListener listenerException = mock(ShoppingListEventListener.class);
			doThrow(new RuntimeException()).when(listenerException).shoppingListEventDetected(SHOP_EVENT);
			// Run
			unitUnderTest.addShoppingListEventListener(listenerException);
			unitUnderTest.addShoppingListEventListener(listener);
			// Check
			unitUnderTest.fireShoppingListEvent(SHOP_EVENT);
			verify(listener, times(1)).shoppingListEventDetected(SHOP_EVENT);
		}

	}

	@Nested
	class TestOfMethod_removeShoppingListEventListener_ShoppingListEventListener {

		@Test
		void passANullValue_noExceptionThrown() {
			unitUnderTest.removeShoppingListEventListener(null);
		}

		@Test
		void passAnEventListener_willNotCallTheListenerWhileProcessingFiredEventsAnyMore() {
			// Prepare
			unitUnderTest.addShoppingListEventListener(listener);
			// Run
			unitUnderTest.removeShoppingListEventListener(listener);
			// Check
			unitUnderTest.fireShoppingListEvent(SHOP_EVENT);
			verifyNoInteractions(listener);
		}

	}

}