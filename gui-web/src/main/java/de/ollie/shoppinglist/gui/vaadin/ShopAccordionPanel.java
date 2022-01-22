package de.ollie.shoppinglist.gui.vaadin;

import com.vaadin.flow.component.accordion.AccordionPanel;

import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.core.service.ListPositionService;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEvent;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEventListener;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEventType;

public class ShopAccordionPanel extends AccordionPanel implements ShoppingListEventListener {

	private ListPositionService listPositionService;
	private Shop shop;
	private ShoppingListPositionsLayout shoppingListPositionsLayout;

	public ShopAccordionPanel(Shop shop, ShoppingListPositionsLayout shoppingListPositionsLayout,
			ListPositionService listPositionService) {
		super(shop.getName(), shoppingListPositionsLayout);
		this.listPositionService = listPositionService;
		this.shop = shop;
		this.shoppingListPositionsLayout = shoppingListPositionsLayout;
		updateSummary();
	}

	public ShoppingListPositionsLayout getShoppingListPositionsLayout() {
		return shoppingListPositionsLayout;
	}

	private void updateSummary() {
		long itemCount = listPositionService.countByShop(shop);
		setSummaryText(shop.getName() + (itemCount > 0 ? " (" + itemCount + ")" : ""));
	}

	public long getShop() {
		return shop != null ? shop.getId() : Long.MIN_VALUE;
	}

	@Override
	public void shoppingListEventDetected(ShoppingListEvent<?> event) {
		if (event.getType() == ShoppingListEventType.LIST_POSITION) {
			updateSummary();
		}
	}

}