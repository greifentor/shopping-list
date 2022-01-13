package de.ollie.shoppinglist.gui.vaadin;

import java.util.stream.Collectors;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.core.service.ShopService;
import de.ollie.shoppinglist.core.service.localization.ResourceManager;
import de.ollie.shoppinglist.gui.SessionData;
import de.ollie.shoppinglist.gui.vaadin.component.Button;
import de.ollie.shoppinglist.gui.vaadin.component.ButtonFactory;

public class ShopMaintenanceLayout extends VerticalLayout {

	private final ResourceManager resourceManager;
	private final SessionData sessionData;
	private final ShopService shopService;

	private Button buttonEdit;
	private Button buttonNew;
	private Button buttonRemove;
	private Grid<Shop> gridShops;

	public ShopMaintenanceLayout(ShopService shopService, ResourceManager resourceManager, SessionData sessionData) {
		this.resourceManager = resourceManager;
		this.sessionData = sessionData;
		this.shopService = shopService;
		gridShops = new Grid<>(10);
		gridShops
				.addColumn(shop -> shop.getName())
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ShopMaintenanceLayout.headers.name.label",
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
		buttonNew =
				ButtonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"ShopMaintainceLayout.buttonNew.text",
												ApplicationStartLayout.LOCALIZATION),
								event -> newShop());
		buttonRemove =
				ButtonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"ShopMaintainceLayout.buttonRemove.text",
												ApplicationStartLayout.LOCALIZATION),
								event -> removeShop());
		layout.add(buttonNew, buttonEdit, new Label(""), buttonRemove);
		return layout;
	}

	private void updateGridShops() {
		gridShops
				.setItems(
						shopService
								.findAll(new PageParameters().setEntriesPerPage(Integer.MAX_VALUE).setPageNumber(0))
								.getEntries()
								.stream()
								.sorted((s0, s1) -> s0.getName().compareTo(s1.getName()))
								.collect(Collectors.toList()));
	}

	private void editShop() {
		System.out.println("pressed edit shop button.");
		if (!gridShops.getSelectedItems().isEmpty()) {
			Shop shop = gridShops.getSelectedItems().toArray(new Shop[1])[0];
			new ShopDetailsDialog(shop, event -> saveShop(shop), resourceManager, sessionData).open();
		}
	}

	private void newShop() {
		System.out.println("pressed new shop button.");
		Shop shop = new Shop().setName("").setUser(sessionData.getAuthorizationData().getUser().getId());
		new ShopDetailsDialog(shop, event -> saveShop(shop), resourceManager, sessionData).open();
	}

	private void saveShop(Shop shop) {
		shopService.update(shop);
		updateGridShops();
	}

	private void removeShop() {
		System.out.println("pressed remove shop button.");
		if (!gridShops.getSelectedItems().isEmpty()) {
			Shop shop = gridShops.getSelectedItems().toArray(new Shop[1])[0];
			shopService.delete(shop);
			updateGridShops();
		}
	}

}