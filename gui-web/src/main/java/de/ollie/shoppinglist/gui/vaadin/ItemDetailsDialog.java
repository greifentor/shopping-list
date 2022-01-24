package de.ollie.shoppinglist.gui.vaadin;

import java.util.stream.Collectors;

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
	private NumberField numberFieldPosition;

	public ItemDetailsDialog(Item item, ItemDetailsDialogObserver observer, ResourceManager resourceManager,
			SessionData sessionData, ShopService shopService) {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(false);
		comboBoxShops = new ComboBox<>();
		comboBoxShops.setWidthFull();
		comboBoxShops.setItemLabelGenerator(shop -> shop.getName());
		comboBoxShops
				.setItems(
						shopService
								.findAll()
								.stream()
								.sorted((i0, i1) -> i0.getName().compareTo(i1.getName()))
								.collect(Collectors.toList()));
		comboBoxShops.setValue(shopService.findById(item.getShop()).orElse(null));
		textFieldName =
				new TextField(
						resourceManager
								.getLocalizedString(
										"ItemDetailsDialog.textFieldName.label",
										sessionData.getLocalization()));
		textFieldName.setWidthFull();
		textFieldName.setValue(item.getName());
		numberFieldPosition =
				new NumberField(
						resourceManager
								.getLocalizedString(
										"ItemDetailsDialog.textFieldName.label",
										sessionData.getLocalization()));
		numberFieldPosition.setWidthFull();
		numberFieldPosition.setMax(Integer.MAX_VALUE);
		numberFieldPosition.setMin(0);
		numberFieldPosition.setStep(1);
		numberFieldPosition.setValue((double) item.getPosition());
		numberFieldPosition.setHasControls(true);
		Button buttonCancel = ButtonFactory.createCancelButton(resourceManager, event -> cancelDialog(), sessionData);
		buttonCancel.setWidthFull();
		Button buttonOk =
				ButtonFactory.createOkButton(resourceManager, event -> submitItem(item, observer), sessionData);
		buttonOk.setWidthFull();
		layout.add(textFieldName, comboBoxShops, numberFieldPosition, buttonCancel, buttonOk);
		add(layout);
	}

	private void cancelDialog() {
		close();
	}

	private void submitItem(Item item, ItemDetailsDialogObserver observer) {
		item.setName(textFieldName.getValue());
		item.setPosition(numberFieldPosition.getValue().intValue());
		item.setShop(comboBoxShops.getValue().getId());
		close();
		observer.itemChanged(item);
	}

}