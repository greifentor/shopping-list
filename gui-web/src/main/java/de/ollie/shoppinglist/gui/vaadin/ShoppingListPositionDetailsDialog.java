package de.ollie.shoppinglist.gui.vaadin;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import de.ollie.shoppinglist.core.model.Item;
import de.ollie.shoppinglist.core.model.ListPosition;
import de.ollie.shoppinglist.core.service.ItemService;
import de.ollie.shoppinglist.core.service.localization.ResourceManager;
import de.ollie.shoppinglist.gui.SessionData;
import de.ollie.shoppinglist.gui.vaadin.component.Button;
import de.ollie.shoppinglist.gui.vaadin.component.ButtonFactory;

public class ShoppingListPositionDetailsDialog extends Dialog {

	public interface ShoppingListPositionRemoveDialogObserver {
		void removeConfirmed(ListPosition listPosition);
	}

	private TextField textFieldNameOfItemToRemove;

	public ShoppingListPositionDetailsDialog(ListPosition listPosition,
			ShoppingListPositionRemoveDialogObserver observer, ItemService itemService, ResourceManager resourceManager,
			SessionData sessionData) {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(false);
		textFieldNameOfItemToRemove =
				new TextField(
						resourceManager
								.getLocalizedString(
										"ShoppingListPositionRemoveDialog.textFieldNameOfItemToRemove.label",
										sessionData.getLocalization()));
		textFieldNameOfItemToRemove.setWidthFull();
		textFieldNameOfItemToRemove
				.setValue(itemService.findById(listPosition.getItem()).map(Item::getName).orElse("-"));
		textFieldNameOfItemToRemove.setEnabled(false);
		Button buttonCancel = ButtonFactory.createCancelButton(resourceManager, event -> cancelDialog(), sessionData);
		buttonCancel.setWidthFull();
		Button buttonRemove =
				ButtonFactory
						.createRemoveButton(
								resourceManager,
								event -> {
									observer.removeConfirmed(listPosition);
									close();
								},
								sessionData);
		buttonRemove.setWidthFull();
		layout.add(textFieldNameOfItemToRemove, buttonCancel, buttonRemove);
		add(layout);
	}

	private void cancelDialog() {
		close();
	}

}