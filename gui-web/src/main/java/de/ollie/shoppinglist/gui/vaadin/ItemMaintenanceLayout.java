package de.ollie.shoppinglist.gui.vaadin;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.shoppinglist.core.model.Item;
import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.core.service.ItemService;
import de.ollie.shoppinglist.core.service.ShopService;
import de.ollie.shoppinglist.core.service.localization.ResourceManager;
import de.ollie.shoppinglist.gui.SessionData;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ActionType;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ItemShoppingListEvent;
import de.ollie.shoppinglist.gui.vaadin.component.Button;
import de.ollie.shoppinglist.gui.vaadin.component.ButtonFactory;

public class ItemMaintenanceLayout extends VerticalLayout {

	private final ShoppingListEventManager eventManager;
	private final ItemService itemService;
	private final ResourceManager resourceManager;
	private final SessionData sessionData;
	private final ShopService shopService;

	private Button buttonEdit;
	private Button buttonNew;
	private Button buttonRemove;
	private Grid<Item> gridItems;

	public ItemMaintenanceLayout(ItemService itemService, ResourceManager resourceManager, SessionData sessionData,
			ShopService shopService, ShoppingListEventManager eventManager) {
		this.eventManager = eventManager;
		this.itemService = itemService;
		this.resourceManager = resourceManager;
		this.sessionData = sessionData;
		this.shopService = shopService;
		gridItems = new Grid<>(10);
		gridItems.setMultiSort(true);
		gridItems
				.addColumn(item -> item.getName())
				.setComparator((i0, i1) -> i0.getName().compareTo(i1.getName()))
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ItemMaintenanceLayout.headers.name.label",
										sessionData.getLocalization()));
		gridItems
				.addColumn(item -> getShopName(item.getShop()))
				.setComparator((i0, i1) -> getShopName(i0.getShop()).compareTo(getShopName(i1.getShop())))
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ItemMaintenanceLayout.headers.shop.label",
										sessionData.getLocalization()));
		gridItems
				.addColumn(item -> item.getSortOrder())
				.setComparator((i0, i1) -> i0.getSortOrder() - i1.getSortOrder())
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ItemMaintenanceLayout.headers.position.label",
										sessionData.getLocalization()));
		gridItems.setWidthFull();
		updateGridItems();
		setMargin(false);
		setWidthFull();
		add(createButtonLayout(), gridItems);
	}

	private String getShopName(Shop shop) {
		return shop != null ? shop.getName() : "n/a";
	}

	private HorizontalLayout createButtonLayout() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(false);
		layout.setWidthFull();
		buttonEdit =
				ButtonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"ItemMaintainceLayout.buttonEdit.text",
												ApplicationStartLayout.LOCALIZATION),
								event -> editItem());
		buttonEdit.setWidthFull();
		buttonNew =
				ButtonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"ItemMaintainceLayout.buttonNew.text",
												ApplicationStartLayout.LOCALIZATION),
								event -> newItem());
		buttonNew.setWidthFull();
		buttonRemove =
				ButtonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"ItemMaintainceLayout.buttonRemove.text",
												ApplicationStartLayout.LOCALIZATION),
								event -> removeItem());
		buttonRemove.setWidthFull();
		layout.add(buttonNew, buttonEdit, new Label(""), buttonRemove);
		return layout;
	}

	private void updateGridItems() {
		gridItems
				.setItems(
						itemService
								.findAllByUser(sessionData.getAuthorizationData().getUser())
								.stream()
								.sorted((i0, i1) -> i0.getName().compareTo(i1.getName())));
	}

	private void editItem() {
		sessionData.getAccessChecker().checkToken();
		if (!gridItems.getSelectedItems().isEmpty()) {
			Item item = gridItems.getSelectedItems().toArray(new Item[1])[0];
			new ItemDetailsDialog(item, event -> saveItem(item), resourceManager, sessionData, shopService).open();
		}
	}

	private void newItem() {
		sessionData.getAccessChecker().checkToken();
		Item item = new Item().setName("").setUser(sessionData.getAuthorizationData().getUser());
		new ItemDetailsDialog(item, event -> saveItem(item), resourceManager, sessionData, shopService).open();
	}

	private void saveItem(Item item) {
		sessionData.getAccessChecker().checkToken();
		itemService.update(item);
		eventManager.fireShoppingListEvent(new ItemShoppingListEvent(ActionType.ADD, item));
		updateGridItems();
	}

	private void removeItem() {
		sessionData.getAccessChecker().checkToken();
		if (!gridItems.getSelectedItems().isEmpty()) {
			Item item = gridItems.getSelectedItems().toArray(new Item[1])[0];
			itemService.delete(item);
			eventManager.fireShoppingListEvent(new ItemShoppingListEvent(ActionType.REMOVE, item));
			updateGridItems();
		}
	}

}