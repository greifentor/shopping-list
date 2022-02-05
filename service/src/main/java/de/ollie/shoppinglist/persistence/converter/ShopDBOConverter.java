package de.ollie.shoppinglist.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import de.ollie.shoppinglist.persistence.entity.ShopDBO;
import de.ollie.shoppinglist.core.model.Shop;

/**
 * A DBO converter for shops.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class ShopDBOConverter implements ToModelConverter<Shop, ShopDBO> {

	private final UserDBOConverter userDBOConverter;

	public ShopDBO toDBO(Shop model) {
		if (model == null) {
			return null;
		}
		return new ShopDBO()
				.setId(model.getId())
				.setUser(userDBOConverter.toDBO(model.getUser()))
				.setName(model.getName())
				.setSortOrder(model.getSortOrder());
	}

	@Override
	public Shop toModel(ShopDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new Shop()
				.setId(dbo.getId())
				.setUser(userDBOConverter.toModel(dbo.getUser()))
				.setName(dbo.getName())
				.setSortOrder(dbo.getSortOrder());
	}

	@Override
	public List<Shop> toModel(List<ShopDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}