package de.ollie.shoppinglist.gui.vaadin;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.core.service.ItemService;
import de.ollie.shoppinglist.core.service.ListPositionService;
import de.ollie.shoppinglist.core.service.ShopService;
import de.ollie.shoppinglist.core.service.localization.ResourceManager;
import de.ollie.shoppinglist.gui.SessionData;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ActionType;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShopShoppingListEvent;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEvent;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEventListener;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEventType;

/**
 * The main layout of the application.
 * 
 * @author ollie (07.01.2022)
 */
public class ShoppingListMainLayout extends VerticalLayout implements ShoppingListEventListener {

	private Accordion accordion;
	private AccordionPanel accordionPanelItem;
	private AccordionPanel accordionPanelShop;
	private ShoppingListEventManager eventManager = new ShoppingListEventManager();
	private ItemService itemService;
	private ListPositionService listPositionService;
	private ResourceManager resourceManager;
	private SessionData sessionData;
	private List<ShopAccordionPanel> shopAccordionPanels = new ArrayList<>();

	public ShoppingListMainLayout(ShopService shopService, ItemService itemService,
			ListPositionService listPositionService, ResourceManager resourceManager, SessionData sessionData) {
		this.itemService = itemService;
		this.listPositionService = listPositionService;
		this.resourceManager = resourceManager;
		this.sessionData = sessionData;
		VerticalLayout layout = new VerticalLayout();
		layout.setWidthFull();
		layout.getStyle().set("-moz-border-radius", "4px");
		layout.getStyle().set("-webkit-border-radius", "4px");
		layout.getStyle().set("border-radius", "4px");
		layout.getStyle().set("border", "1px solid LightSteelBlue");
		accordion = new Accordion();
		accordion.setWidthFull();
		shopService
				.findAll()
				.stream()
				.filter(shop -> shop.getUser().equals(sessionData.getAuthorizationData().getUser().getId()))
				.forEach(shop -> shopAccordionPanels.add(createShopAccordionPanel(shop)));
		accordionPanelShop =
				new AccordionPanel(
								resourceManager
										.getLocalizedString(
												"ShoppingListMainLayout.accordion.tab.shops",
												sessionData.getLocalization()),
						new ShopMaintenanceLayout(shopService, resourceManager, sessionData, eventManager));
		accordionPanelItem =
				new AccordionPanel(
								resourceManager
										.getLocalizedString(
												"ShoppingListMainLayout.accordion.tab.items",
												sessionData.getLocalization()),
								new ItemMaintenanceLayout(
										itemService,
										resourceManager,
										sessionData,
										shopService,
								eventManager));
		updateAccordion();
		layout.add(accordion);
		add(layout);
		eventManager.addShoppingListEventListener(this);
	}

	private void updateAccordion() {
		accordion.getChildren().forEach(accordion::remove);
		shopAccordionPanels.forEach(accordion::add);
		accordion.add(accordionPanelShop);
		accordion.add(accordionPanelItem);
	}

	private ShopAccordionPanel createShopAccordionPanel(Shop shop) {
		ShoppingListPositionsLayout layout =
				new ShoppingListPositionsLayout(
						eventManager,
						itemService,
						listPositionService,
						resourceManager,
						shop,
						sessionData);
		eventManager.addShoppingListEventListener(layout);
		ShopAccordionPanel shopAccordionPanel = new ShopAccordionPanel(shop, layout, listPositionService);
		eventManager.addShoppingListEventListener(shopAccordionPanel);
		return shopAccordionPanel;
	}

	@Override
	public void shoppingListEventDetected(ShoppingListEvent<?> event) {
		System.out.println(event);
		if (event.getType() == ShoppingListEventType.SHOP) {
			ShopShoppingListEvent shopEvent = (ShopShoppingListEvent) event;
			if (event.getAction() == ActionType.ADD) {
				shopAccordionPanels.add(createShopAccordionPanel(shopEvent.getValue()));
				updateAccordion();
			} else if (event.getAction() == ActionType.REMOVE) {
				accordion
						.getChildren()
						.filter(component -> component instanceof ShopAccordionPanel)
						.map(component -> (ShopAccordionPanel) component)
						.filter(shopAccordionPanel -> shopAccordionPanel.getShop() == shopEvent.getValue().getId())
						.findFirst()
						.ifPresent(shopAccordionPanel -> {
							removeShopAccordionPanel(shopAccordionPanel);
							eventManager
									.removeShoppingListEventListener(
											shopAccordionPanel.getShoppingListPositionsLayout());
							eventManager.removeShoppingListEventListener(shopAccordionPanel);
						});
			}
		}

	}

	private void removeShopAccordionPanel(ShopAccordionPanel shopAccordionPanel) {
		shopAccordionPanels.remove(shopAccordionPanel);
		accordion.remove(shopAccordionPanel);
		updateAccordion();
	}

}