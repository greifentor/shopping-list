package de.ollie.shoppinglist.gui.vaadin;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.shoppinglist.core.model.Item;
import de.ollie.shoppinglist.core.model.ListPosition;
import de.ollie.shoppinglist.core.model.Shop;
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

	private final ShoppingListEventManager eventManager;
	private final ItemService itemService;
	private final ListPositionService listPositionService;
	private final ResourceManager resourceManager;
	private final Shop shop;
	private final SessionData sessionData;

	private Button buttonAddPosition;
	private ComboBox<String> comboBoxItems;
	private Grid<ListPosition> gridListPositions;

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		removeAll();
		buttonAddPosition =
				ButtonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"ShoppingListPositionsLayout.button.addposition.label",
												sessionData.getLocalization()),
								event -> addListPosition());
		comboBoxItems = new ComboBox<>();
		comboBoxItems.setWidthFull();
		comboBoxItems.setAllowCustomValue(true);
		gridListPositions = new Grid<>();
		updateComboBoxItem();
		gridListPositions
				.addColumn(listPosition -> listPosition.getDescription())
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ShoppingListPositionsLayout.headers.description.label",
										sessionData.getLocalization()));
		gridListPositions.setWidthFull();
		gridListPositions.addItemDoubleClickListener(event -> removePosition(event.getItem()));
		updateGridListPositions();
		add(comboBoxItems, buttonAddPosition, gridListPositions);
	}

	private void updateComboBoxItem() {
		if (comboBoxItems != null) {
			comboBoxItems
					.setItems(
							itemService
									.findAll()
									.stream()
									.filter(
											item -> (item.getUser() != null)
													|| (item.getUser().longValue() == sessionData
															.getAuthorizationData()
															.getUser()
															.getId()))
									.filter(item -> (item.getShop() == shop.getId()))
									.map(Item::getName));
		}
	}

	private void addListPosition() {
		if (comboBoxItems.getValue() != null) {
			ListPosition newListPosition =
					new ListPosition()
							.setDescription(comboBoxItems.getValue())
							.setShop(shop.getId())
							.setUser(sessionData.getAuthorizationData().getUser().getId());
			listPositionService.create(newListPosition);
			updateGridListPositions();
			eventManager.fireShoppingListEvent(new ListPositionShoppingListEvent(ActionType.ADD, newListPosition));
		}
	}

	private void removePosition(ListPosition listPosition) {
		if (listPosition != null) {
			listPositionService.delete(listPosition);
			updateGridListPositions();
			eventManager.fireShoppingListEvent(new ListPositionShoppingListEvent(ActionType.REMOVE, listPosition));
		}
	}

	private void updateGridListPositions() {
		if (gridListPositions != null) {
			gridListPositions.setItems(listPositionService.findAll().stream().filter(this::matchesToShopAndUser));
		}
	}

	private boolean matchesToShopAndUser(ListPosition listPosition) {
		return isShopMatching(listPosition.getShop()) && isUserMatching(listPosition.getUser());
	}

	private boolean isShopMatching(Long shopId) {
		return (shopId != null) && (shop.getId() == shopId);
	}

	private boolean isUserMatching(long userId) {
		return userId == sessionData.getAuthorizationData().getUser().getId();
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		// TODO Auto-generated method stub
		super.onDetach(detachEvent);
	}

	@Override
	public void shoppingListEventDetected(ShoppingListEvent<?> event) {
		if (event.getType() == ShoppingListEventType.ITEM) {
			updateComboBoxItem();
		}
	}

}