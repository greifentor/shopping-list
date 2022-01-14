package de.ollie.shoppinglist.gui.vaadin;

import java.util.stream.Collectors;

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
import de.ollie.shoppinglist.gui.vaadin.component.Button;
import de.ollie.shoppinglist.gui.vaadin.component.ButtonFactory;

public class ItemMaintenanceLayout extends VerticalLayout {

	private final ResourceManager resourceManager;
	private final ItemService itemService;
	private final SessionData sessionData;
	private final ShopService shopService;

	private Button buttonEdit;
	private Button buttonNew;
	private Button buttonRemove;
	private Grid<Item> gridItems;

	public ItemMaintenanceLayout(ItemService itemService, ResourceManager resourceManager, SessionData sessionData,
			ShopService shopService) {
		this.resourceManager = resourceManager;
		this.sessionData = sessionData;
		this.shopService = shopService;
		this.itemService = itemService;
		gridItems = new Grid<>(10);
		gridItems
				.addColumn(shop -> shop.getName())
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ItemMaintenanceLayout.headers.name.label",
										sessionData.getLocalization()));
		gridItems
				.addColumn(item -> shopService.findById(item.getShop()).map(Shop::getName).orElse("n/a"))
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ItemMaintenanceLayout.headers.shop.label",
										sessionData.getLocalization()));
		gridItems.setWidthFull();
		updateGridItems();
		setMargin(false);
		setWidthFull();
		add(createButtonLayout(), gridItems);
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
		buttonNew =
				ButtonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"ItemMaintainceLayout.buttonNew.text",
												ApplicationStartLayout.LOCALIZATION),
								event -> newItem());
		buttonRemove =
				ButtonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"ItemMaintainceLayout.buttonRemove.text",
												ApplicationStartLayout.LOCALIZATION),
								event -> removeItem());
		layout.add(buttonNew, buttonEdit, new Label(""), buttonRemove);
		return layout;
	}

	private void updateGridItems() {
		gridItems
				.setItems(
						itemService
								.findAll()
								.stream()
								.sorted((i0, i1) -> i0.getName().compareTo(i1.getName()))
								.collect(Collectors.toList()));
	}

	private void editItem() {
		System.out.println("pressed edit item button.");
		if (!gridItems.getSelectedItems().isEmpty()) {
			Item item = gridItems.getSelectedItems().toArray(new Item[1])[0];
			new ItemDetailsDialog(item, event -> saveItem(item), resourceManager, sessionData, shopService).open();
		}
	}

	private void newItem() {
		System.out.println("pressed new item button.");
		Item item = new Item().setName("").setUser(sessionData.getAuthorizationData().getUser().getId());
		new ItemDetailsDialog(item, event -> saveItem(item), resourceManager, sessionData, shopService).open();
	}

	private void saveItem(Item item) {
		itemService.update(item);
		updateGridItems();
	}

	private void removeItem() {
		System.out.println("pressed remove item button.");
		if (!gridItems.getSelectedItems().isEmpty()) {
			Item item = gridItems.getSelectedItems().toArray(new Item[1])[0];
			itemService.delete(item);
			updateGridItems();
		}
	}

}