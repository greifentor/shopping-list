package de.ollie.shoppinglist.persistence.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.shoppinglist.persistence.entity.ShopDBO;
import de.ollie.shoppinglist.core.model.Shop;

/**
 * A DBO converter for shops.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class ShopDBOConverter implements ToModelConverter<Shop, ShopDBO> {

	public ShopDBO toDBO(Shop model) {
		if (model == null) {
			return null;
		}
		return new ShopDBO()
				.setId(model.getId())
				.setUser(model.getUser())
				.setName(model.getName());
	}

	@Override
	public Shop toModel(ShopDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new Shop()
				.setId(dbo.getId())
				.setUser(dbo.getUser())
				.setName(dbo.getName());
	}

}