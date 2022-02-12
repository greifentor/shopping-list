package de.ollie.shoppinglist.gui.vaadin;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.shoppinglist.core.model.Item;
import de.ollie.shoppinglist.core.model.ListPosition;
import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.core.model.User;
import de.ollie.shoppinglist.core.service.ItemService;
import de.ollie.shoppinglist.core.service.ListPositionService;
import de.ollie.shoppinglist.core.service.localization.ResourceManager;
import de.ollie.shoppinglist.gui.SessionData;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ActionType;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ListPositionShoppingListEvent;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEvent;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEventListener;
import de.ollie.shoppinglist.gui.vaadin.ShoppingListEventManager.ShoppingListEventType;
import de.ollie.shoppinglist.gui.vaadin.component.Button;
import de.ollie.shoppinglist.gui.vaadin.component.ButtonFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShoppingListPositionsLayout extends VerticalLayout implements ShoppingListEventListener {

	private static final Logger logger = LogManager.getLogger(ShoppingListPositionsLayout.class);

	private final ShoppingListEventManager eventManager;
	private final ItemService itemService;
	private final ListPositionService listPositionService;
	private final ResourceManager resourceManager;
	private final Shop shop;
	private final SessionData sessionData;

	private Button buttonAddPosition;
	private ComboBox<Item> comboBoxItems;
	private Grid<ListPosition> gridListPositions;

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		if (!sessionData.getAccessChecker().checkToken()) {
			return;
		}
		removeAll();
		buttonAddPosition =
				ButtonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"ShoppingListPositionsLayout.button.addposition.label",
												sessionData.getLocalization()),
								event -> addListPosition());
		buttonAddPosition.setWidthFull();
		comboBoxItems = new ComboBox<>();
		comboBoxItems.setWidthFull();
		comboBoxItems.setAllowCustomValue(true);
		comboBoxItems.setItemLabelGenerator(item -> item.getName());
		gridListPositions = new Grid<>();
		updateComboBoxItem();
		gridListPositions
				.addColumn(listPosition -> getItemName(listPosition.getItem()))
				.setComparator((lp0, lp1) -> getItemName(lp0.getItem()).compareTo(getItemName(lp1.getItem())))
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ShoppingListPositionsLayout.headers.description.label",
										sessionData.getLocalization()));
		gridListPositions
				.addColumn(listPosition -> getSortOrder(listPosition.getItem()))
				.setComparator((lp0, lp1) -> getSortOrder(lp0.getItem()) - getSortOrder(lp1.getItem()))
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ShoppingListPositionsLayout.headers.sortOrder.label",
										sessionData.getLocalization()));
		gridListPositions.setWidthFull();
		gridListPositions.addItemClickListener(event -> removePosition(event.getItem()));
		updateGridListPositions();
		add(comboBoxItems, buttonAddPosition, gridListPositions);
		logger.info("attached");
	}

	private String getItemName(Item item) {
		return item != null ? item.getName() : "-";
	}

	private void updateComboBoxItem() {
		if (comboBoxItems != null) {
			comboBoxItems
					.setItems(
							itemService
									.findAllByUser(sessionData.getAuthorizationData().getUser())
									.stream()
									.filter(item -> (item.getShop().getId() == shop.getId()))
									.sorted((i0, i1) -> i0.getName().compareTo(i1.getName()))
									.collect(Collectors.toList()));
		}
	}

	private void addListPosition() {
		sessionData.getAccessChecker().checkToken();
		if (comboBoxItems.getValue() != null) {
			ListPosition newListPosition =
					new ListPosition()
							.setItem(comboBoxItems.getValue())
							.setShop(shop)
							.setUser(sessionData.getAuthorizationData().getUser());
			listPositionService.create(newListPosition);
			updateGridListPositions();
			eventManager.fireShoppingListEvent(new ListPositionShoppingListEvent(ActionType.ADD, newListPosition));
		}
	}

	private void removePosition(ListPosition listPosition) {
		sessionData.getAccessChecker().checkToken();
		if (listPosition != null) {
			new ShoppingListPositionDetailsDialog(listPosition, lp -> {
				listPositionService.delete(lp);
			updateGridListPositions();
				eventManager.fireShoppingListEvent(new ListPositionShoppingListEvent(ActionType.REMOVE, lp));
			}, resourceManager, sessionData).open();
		}
	}

	private void updateGridListPositions() {
		sessionData.getAccessChecker().checkToken();
		if (gridListPositions != null) {
			gridListPositions
					.setItems(
							listPositionService
									.findAll()
									.stream()
									.filter(this::matchesToShopAndUser)
									.sorted((lp0, lp1) -> getSortOrder(lp0.getItem()) - getSortOrder(lp1.getItem()))
									.collect(Collectors.toList()));
		}
	}

	private int getSortOrder(Item item) {
		return item != null ? item.getSortOrder() : 0;
	}

	private boolean matchesToShopAndUser(ListPosition listPosition) {
		return isShopMatching(listPosition.getShop()) && isUserMatching(listPosition.getUser());
	}

	private boolean isShopMatching(Shop listPositionShop) {
		return (shop != null) && (shop.getId() == (listPositionShop != null ? listPositionShop.getId() : -1));
	}

	private boolean isUserMatching(User listPositionUser) {
		return (listPositionUser != null ? listPositionUser.getId() : -1) == sessionData
				.getAuthorizationData()
				.getUser()
				.getId();
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		super.onDetach(detachEvent);
		sessionData.getAccessChecker().checkToken();
		logger.info("detached");
	}

	@Override
	public void shoppingListEventDetected(ShoppingListEvent<?> event) {
		if (event.getType() == ShoppingListEventType.ITEM) {
			updateComboBoxItem();
		}
	}

}