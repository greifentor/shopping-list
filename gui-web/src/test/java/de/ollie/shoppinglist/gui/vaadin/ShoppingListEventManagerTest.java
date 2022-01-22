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

import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEvent;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEventListener;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEventType;

@ExtendWith(MockitoExtension.class)
public class ShoppingListEventManagerTest {

	private static final ShoppingListEvent ITEM_EVENT = new ShoppingListEvent(ShoppingListEventType.ITEM);
	private static final ShoppingListEvent SHOP_EVENT = new ShoppingListEvent(ShoppingListEventType.SHOP);

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