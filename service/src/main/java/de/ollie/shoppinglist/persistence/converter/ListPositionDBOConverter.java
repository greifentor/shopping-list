package de.ollie.shoppinglist.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import de.ollie.shoppinglist.persistence.entity.ListPositionDBO;
import de.ollie.shoppinglist.core.model.ListPosition;

/**
 * A DBO converter for list_positions.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class ListPositionDBOConverter implements ToModelConverter<ListPosition, ListPositionDBO> {

	private final ItemDBOConverter itemDBOConverter;
	private final ShopDBOConverter shopDBOConverter;
	private final UserDBOConverter userDBOConverter;

	public ListPositionDBO toDBO(ListPosition model) {
		if (model == null) {
			return null;
		}
		return new ListPositionDBO()
				.setId(model.getId())
				.setItem(itemDBOConverter.toDBO(model.getItem()))
				.setShop(shopDBOConverter.toDBO(model.getShop()))
				.setUser(userDBOConverter.toDBO(model.getUser()));
	}

	@Override
	public ListPosition toModel(ListPositionDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new ListPosition()
				.setId(dbo.getId())
				.setItem(itemDBOConverter.toModel(dbo.getItem()))
				.setShop(shopDBOConverter.toModel(dbo.getShop()))
				.setUser(userDBOConverter.toModel(dbo.getUser()));
	}

	@Override
	public List<ListPosition> toModel(List<ListPositionDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}