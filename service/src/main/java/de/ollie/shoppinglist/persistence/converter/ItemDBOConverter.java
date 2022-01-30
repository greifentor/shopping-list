package de.ollie.shoppinglist.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import de.ollie.shoppinglist.core.model.Item;
import de.ollie.shoppinglist.persistence.entity.ItemDBO;
import de.ollie.shoppinglist.persistence.repository.ShopDBORepository;
import de.ollie.shoppinglist.persistence.repository.UserDBORepository;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * A DBO converter for items.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class ItemDBOConverter implements ToModelConverter<Item, ItemDBO> {

	private final ShopDBORepository shopDBORepository;
	private final UserDBORepository userDBORepository;

	public ItemDBO toDBO(Item model) {
		if (model == null) {
			return null;
		}
		return new ItemDBO()
				.setId(model.getId())
				.setShop((model.getShop() < 1 ? null : shopDBORepository.findById(model.getShop()).orElse(null)))
				.setUser((model.getUser() == null ? null : userDBORepository.findById(model.getUser()).orElse(null)))
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
				.setShop((dbo.getShop() == null ? null : dbo.getShop().getId()))
				.setUser((dbo.getUser() == null ? null : dbo.getUser().getId()))
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