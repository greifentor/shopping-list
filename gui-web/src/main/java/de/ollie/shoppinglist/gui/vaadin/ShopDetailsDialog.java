package de.ollie.shoppinglist.gui.vaadin;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.core.service.localization.ResourceManager;
import de.ollie.shoppinglist.gui.SessionData;
import de.ollie.shoppinglist.gui.vaadin.component.Button;
import de.ollie.shoppinglist.gui.vaadin.component.ButtonFactory;

public class ShopDetailsDialog extends Dialog {

	public interface ShopDetailsDialogObserver {
		void shopChanged(Shop shop);
	}

	private NumberField numberFieldSortOrder;
	private TextField textFieldName;

	public ShopDetailsDialog(Shop shop, ShopDetailsDialogObserver observer, ResourceManager resourceManager,
			SessionData sessionData) {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(false);
		textFieldName =
				new TextField(
						resourceManager
								.getLocalizedString(
										"ShopDetailsDialog.textFieldName.label",
										sessionData.getLocalization()));
		textFieldName.setWidthFull();
		textFieldName.setValue(shop.getName());
		numberFieldSortOrder =
				new NumberField(
						resourceManager
								.getLocalizedString(
										"ItemDetailsDialog.textFieldName.label",
										sessionData.getLocalization()));
		numberFieldSortOrder.setWidthFull();
		numberFieldSortOrder.setMax(Integer.MAX_VALUE);
		numberFieldSortOrder.setMin(0);
		numberFieldSortOrder.setStep(1);
		numberFieldSortOrder.setValue((double) shop.getSortOrder());
		numberFieldSortOrder.setHasControls(true);
		Button buttonCancel = ButtonFactory.createCancelButton(resourceManager, event -> cancelDialog(), sessionData);
		buttonCancel.setWidthFull();
		Button buttonOk =
				ButtonFactory.createOkButton(resourceManager, event -> submitShop(shop, observer), sessionData);
		buttonOk.setWidthFull();
		layout.add(textFieldName, numberFieldSortOrder, buttonCancel, buttonOk);
		add(layout);
	}

	private void cancelDialog() {
		close();
	}

	private void submitShop(Shop shop, ShopDetailsDialogObserver observer) {
		shop.setName(textFieldName.getValue());
		shop.setSortOrder(numberFieldSortOrder.getValue().intValue());
		close();
		observer.shopChanged(shop);
	}

}