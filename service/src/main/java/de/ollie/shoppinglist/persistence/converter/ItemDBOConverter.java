package de.ollie.shoppinglist.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.shoppinglist.persistence.entity.ItemDBO;
import de.ollie.shoppinglist.core.model.Item;

/**
 * A DBO converter for items.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class ItemDBOConverter implements ToModelConverter<Item, ItemDBO> {

	public ItemDBO toDBO(Item model) {
		if (model == null) {
			return null;
		}
		return new ItemDBO()
				.setId(model.getId())
				.setShop(model.getShop())
				.setUser(model.getUser())
				.setName(model.getName());
	}

	@Override
	public Item toModel(ItemDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new Item()
				.setId(dbo.getId())
				.setShop(dbo.getShop())
				.setUser(dbo.getUser())
				.setName(dbo.getName());
	}

	@Override
	public List<Item> toModel(List<ItemDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}