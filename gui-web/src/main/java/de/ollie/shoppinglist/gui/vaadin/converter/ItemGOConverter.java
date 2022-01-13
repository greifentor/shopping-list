package de.ollie.shoppinglist.gui.vaadin.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.shoppinglist.gui.vaadin.go.ItemGO;
import de.ollie.shoppinglist.core.model.Item;

/**
 * A GO converter for items.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class ItemGOConverter implements ToGOConverter<ItemGO, Item> {

	public ItemGO toGO(Item model) {
		if (model == null) {
			return null;
		}
		return new ItemGO()
				.setId(model.getId())
				.setShop(model.getShop())
				.setUser(model.getUser())
				.setName(model.getName());
	}

	public Item toModel(ItemGO go) {
		if (go == null) {
			return null;
		}
		return new Item()
				.setId(go.getId())
				.setShop(go.getShop())
				.setUser(go.getUser())
				.setName(go.getName());
	}

}