package de.ollie.shoppinglist.gui.vaadin;

import java.util.stream.Collectors;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.core.service.ShopService;
import de.ollie.shoppinglist.core.service.localization.ResourceManager;
import de.ollie.shoppinglist.gui.SessionData;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ActionType;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShopShoppingListEvent;
import de.ollie.shoppinglist.gui.vaadin.component.Button;
import de.ollie.shoppinglist.gui.vaadin.component.ButtonFactory;;

public class ShopMaintenanceLayout extends VerticalLayout {

	private final ResourceManager resourceManager;
	private final SessionData sessionData;
	private final ShopService shopService;
	private final ShoppingListEventManager eventManager;

	private Button buttonEdit;
	private Button buttonNew;
	private Button buttonRemove;
	private Grid<Shop> gridShops;

	public ShopMaintenanceLayout(ShopService shopService, ResourceManager resourceManager, SessionData sessionData,
			ShoppingListEventManager eventManager) {
		this.eventManager = eventManager;
		this.resourceManager = resourceManager;
		this.sessionData = sessionData;
		this.shopService = shopService;
		gridShops = new Grid<>(10);
		gridShops
				.addColumn(shop -> shop.getName())
				.setComparator((s0, s1) -> s0.getName().compareTo(s1.getName()))
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ShopMaintenanceLayout.headers.name.label",
										sessionData.getLocalization()));
		gridShops
				.addColumn(shop -> shop.getSortOrder())
				.setComparator((s0, s1) -> s0.getSortOrder() - s1.getSortOrder())
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ShopMaintenanceLayout.headers.sortOrder.label",
										sessionData.getLocalization()));
		gridShops.setWidthFull();
		updateGridShops();
		setMargin(false);
		setWidthFull();
		add(createButtonLayout(), gridShops);
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
												"ShopMaintainceLayout.buttonEdit.text",
												ApplicationStartLayout.LOCALIZATION),
								event -> editShop());
		buttonEdit.setWidthFull();
		buttonNew =
				ButtonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"ShopMaintainceLayout.buttonNew.text",
												ApplicationStartLayout.LOCALIZATION),
								event -> newShop());
		buttonNew.setWidthFull();
		buttonRemove =
				ButtonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"ShopMaintainceLayout.buttonRemove.text",
												ApplicationStartLayout.LOCALIZATION),
								event -> removeShop());
		buttonRemove.setWidthFull();
		layout.add(buttonNew, buttonEdit, new Label(""), buttonRemove);
		return layout;
	}

	private void updateGridShops() {
		if (gridShops != null) {
			gridShops
					.setItems(
							shopService
									.findAllByUser(sessionData.getAuthorizationData().getUser())
									.stream()
									.sorted((s0, s1) -> s0.getName().compareTo(s1.getName()))
									.collect(Collectors.toList()));
		}
	}

	private void editShop() {
		sessionData.getAccessChecker().checkToken();
		if (!gridShops.getSelectedItems().isEmpty()) {
			Shop shop = gridShops.getSelectedItems().toArray(new Shop[1])[0];
			new ShopDetailsDialog(shop, event -> saveShop(shop), resourceManager, sessionData).open();
		}
	}

	private void newShop() {
		sessionData.getAccessChecker().checkToken();
		Shop shop = new Shop().setName("").setUser(sessionData.getAuthorizationData().getUser());
		new ShopDetailsDialog(shop, event -> saveShop(shop), resourceManager, sessionData).open();
	}

	private void saveShop(Shop shop) {
		sessionData.getAccessChecker().checkToken();
		shop = shopService.update(shop);
		updateGridShops();
		eventManager.fireShoppingListEvent(new ShopShoppingListEvent(ActionType.ADD, shop));
	}

	private void removeShop() {
		sessionData.getAccessChecker().checkToken();
		if (!gridShops.getSelectedItems().isEmpty()) {
			Shop shop = gridShops.getSelectedItems().toArray(new Shop[1])[0];
			shopService.delete(shop);
			updateGridShops();
			eventManager.fireShoppingListEvent(new ShopShoppingListEvent(ActionType.REMOVE, shop));
		}
	}

}