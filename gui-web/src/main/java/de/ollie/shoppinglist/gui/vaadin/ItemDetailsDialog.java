package de.ollie.shoppinglist.gui.vaadin;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import de.ollie.shoppinglist.core.model.Item;
import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.core.service.ShopService;
import de.ollie.shoppinglist.core.service.localization.ResourceManager;
import de.ollie.shoppinglist.gui.SessionData;
import de.ollie.shoppinglist.gui.vaadin.component.Button;
import de.ollie.shoppinglist.gui.vaadin.component.ButtonFactory;

public class ItemDetailsDialog extends Dialog {

	public interface ItemDetailsDialogObserver {
		void itemChanged(Item item);
	}

	private ComboBox<Shop> comboBoxShops;
	private TextField textFieldName;
	private NumberField numberFieldSortOrder;
	private ResourceManager resourceManager;
	private SessionData sessionData;

	public ItemDetailsDialog(Item item, ItemDetailsDialogObserver observer, ResourceManager resourceManager,
			SessionData sessionData, ShopService shopService) {
		this.resourceManager = resourceManager;
		this.sessionData = sessionData;
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(false);
		comboBoxShops = new ComboBox<>();
		comboBoxShops.setWidthFull();
		comboBoxShops.setItemLabelGenerator(shop -> shop.getName());
		comboBoxShops
				.setLabel(
						resourceManager
								.getLocalizedString(
										"ItemDetailsDialog.comboBoxShops.label",
										sessionData.getLocalization()));
		comboBoxShops
				.setItems(
						shopService
								.findAllByUser(sessionData.getAuthorizationData().getUser())
								.stream()
								.sorted((i0, i1) -> i0.getName().compareTo(i1.getName())));
		comboBoxShops.setRequired(true);
		comboBoxShops.setValue(item.getShop());
		textFieldName =
				new TextField(
						resourceManager
								.getLocalizedString(
										"ItemDetailsDialog.textFieldName.label",
										sessionData.getLocalization()));
		textFieldName.setWidthFull();
		textFieldName.setValue(item.getName());
		textFieldName.setRequired(true);
		numberFieldSortOrder =
				new NumberField(
						resourceManager
								.getLocalizedString(
										"ItemDetailsDialog.numberFieldSortOrder.label",
										sessionData.getLocalization()));
		numberFieldSortOrder.setWidthFull();
		numberFieldSortOrder.setMax(Integer.MAX_VALUE);
		numberFieldSortOrder.setMin(0);
		numberFieldSortOrder.setStep(1);
		numberFieldSortOrder.setValue((double) item.getSortOrder());
		numberFieldSortOrder.setHasControls(true);
		Button buttonCancel = ButtonFactory.createCancelButton(resourceManager, event -> cancelDialog(), sessionData);
		buttonCancel.setWidthFull();
		Button buttonOk =
				ButtonFactory.createOkButton(resourceManager, event -> submitItem(item, observer), sessionData);
		buttonOk.setWidthFull();
		layout.add(textFieldName, comboBoxShops, numberFieldSortOrder, buttonCancel, buttonOk);
		add(layout);
	}

	private void cancelDialog() {
		close();
	}

	private void submitItem(Item item, ItemDetailsDialogObserver observer) {
		item.setName(textFieldName.getValue());
		item.setSortOrder(numberFieldSortOrder.getValue().intValue());
		item.setShop(comboBoxShops.getValue());
		if ((item.getName() == null) || item.getName().isEmpty()) {
			textFieldName.getStyle().set("color", "red");
			textFieldName
					.setPlaceholder(
							resourceManager
									.getLocalizedString(
											"ItemDetailsDialog.textFieldName.errorMessageNotFilled",
											sessionData.getLocalization()));
			textFieldName.focus();
			return;
		} else {
			textFieldName.getStyle().set("color", "black");
		}
		if (item.getShop() == null) {
			comboBoxShops.focus();
			return;
		}
		close();
		observer.itemChanged(item);
	}

}