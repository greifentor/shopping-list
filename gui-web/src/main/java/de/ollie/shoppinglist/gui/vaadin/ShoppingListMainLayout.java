package de.ollie.shoppinglist.gui.vaadin;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.shoppinglist.core.service.ItemService;
import de.ollie.shoppinglist.core.service.ListPositionService;
import de.ollie.shoppinglist.core.service.ShopService;
import de.ollie.shoppinglist.core.service.localization.ResourceManager;
import de.ollie.shoppinglist.gui.SessionData;

/**
 * The main layout of the application.
 * 
 * @author ollie (07.01.2022)
 */
public class ShoppingListMainLayout extends VerticalLayout {

	private Accordion accordion;

	public ShoppingListMainLayout(ShopService shopService, ItemService itemService,
			ListPositionService listPositionService, ResourceManager resourceManager,
			SessionData sessionData) {
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
				.forEach(
						shop -> accordion
								.add(
										new AccordionPanel(
												shop.getName(),
												new ShoppingListPositionsLayout(
														itemService,
														listPositionService,
														resourceManager,
														shop,
														sessionData))));
		accordion
				.add(
						new AccordionPanel(
								resourceManager
										.getLocalizedString(
												"ShoppingListMainLayout.accordion.tab.shops",
												sessionData.getLocalization()),
								new ShopMaintenanceLayout(shopService, resourceManager, sessionData)));
		accordion
				.add(
						new AccordionPanel(
								resourceManager
										.getLocalizedString(
												"ShoppingListMainLayout.accordion.tab.items",
												sessionData.getLocalization()),
								new ItemMaintenanceLayout(itemService, resourceManager, sessionData, shopService)));
		layout.add(accordion);
		add(layout);
	}

}