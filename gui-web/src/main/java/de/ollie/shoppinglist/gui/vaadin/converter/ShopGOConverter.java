package de.ollie.shoppinglist.gui.vaadin.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.shoppinglist.gui.vaadin.go.ShopGO;
import de.ollie.shoppinglist.core.model.Shop;

/**
 * A GO converter for shops.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class ShopGOConverter implements ToGOConverter<ShopGO, Shop> {

	public ShopGO toGO(Shop model) {
		if (model == null) {
			return null;
		}
		return new ShopGO()
				.setId(model.getId())
				.setUser(model.getUser())
				.setName(model.getName());
	}

	public Shop toModel(ShopGO go) {
		if (go == null) {
			return null;
		}
		return new Shop()
				.setId(go.getId())
				.setUser(go.getUser())
				.setName(go.getName());
	}

}