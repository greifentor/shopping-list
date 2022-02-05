package de.ollie.shoppinglist.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import de.ollie.shoppinglist.persistence.entity.ItemDBO;
import de.ollie.shoppinglist.core.model.Item;

/**
 * A DBO converter for items.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class ItemDBOConverter implements ToModelConverter<Item, ItemDBO> {

	private final ShopDBOConverter shopDBOConverter;
	private final UserDBOConverter userDBOConverter;

	public ItemDBO toDBO(Item model) {
		if (model == null) {
			return null;
		}
		return new ItemDBO()
				.setId(model.getId())
				.setShop(shopDBOConverter.toDBO(model.getShop()))
				.setUser(userDBOConverter.toDBO(model.getUser()))
				.setName(model.getName())
				.setSortOrder(model.getSortOrder());
	}

	@Override
	public Item toModel(ItemDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new Item()
				.setId(dbo.getId())
				.setShop(shopDBOConverter.toModel(dbo.getShop()))
				.setUser(userDBOConverter.toModel(dbo.getUser()))
				.setName(dbo.getName())
				.setSortOrder(dbo.getSortOrder());
	}

	@Override
	public List<Item> toModel(List<ItemDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}