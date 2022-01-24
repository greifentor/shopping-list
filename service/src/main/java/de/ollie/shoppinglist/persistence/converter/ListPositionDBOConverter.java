package de.ollie.shoppinglist.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.shoppinglist.persistence.entity.ListPositionDBO;
import de.ollie.shoppinglist.core.model.ListPosition;

/**
 * A DBO converter for list_positions.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class ListPositionDBOConverter implements ToModelConverter<ListPosition, ListPositionDBO> {

	public ListPositionDBO toDBO(ListPosition model) {
		if (model == null) {
			return null;
		}
		return new ListPositionDBO()
				.setId(model.getId())
				.setItem(model.getItem())
				.setShop(model.getShop())
				.setUser(model.getUser());
	}

	@Override
	public ListPosition toModel(ListPositionDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new ListPosition()
				.setId(dbo.getId())
				.setItem(dbo.getItem())
				.setShop(dbo.getShop())
				.setUser(dbo.getUser());
	}

	@Override
	public List<ListPosition> toModel(List<ListPositionDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}