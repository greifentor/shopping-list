package de.ollie.shoppinglist.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import de.ollie.shoppinglist.core.model.ListPosition;
import de.ollie.shoppinglist.persistence.entity.ListPositionDBO;
import de.ollie.shoppinglist.persistence.repository.ItemDBORepository;
import de.ollie.shoppinglist.persistence.repository.ShopDBORepository;
import de.ollie.shoppinglist.persistence.repository.UserDBORepository;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * A DBO converter for list_positions.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class ListPositionDBOConverter implements ToModelConverter<ListPosition, ListPositionDBO> {

	private final ItemDBORepository itemDBORepository;
	private final ShopDBORepository shopDBORepository;
	private final UserDBORepository userDBORepository;

	public ListPositionDBO toDBO(ListPosition model) {
		if (model == null) {
			return null;
		}
		return new ListPositionDBO()
				.setId(model.getId())
				.setItem((model.getItem() < 1 ? null : itemDBORepository.findById(model.getItem()).orElse(null)))
				.setShop((model.getShop() < 1 ? null : shopDBORepository.findById(model.getShop()).orElse(null)))
				.setUser((model.getUser() < 1 ? null : userDBORepository.findById(model.getUser()).orElse(null)));
	}

	@Override
	public ListPosition toModel(ListPositionDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new ListPosition()
				.setId(dbo.getId())
				.setItem((dbo.getItem() == null ? null : dbo.getItem().getId()))
				.setShop((dbo.getShop() == null ? null : dbo.getShop().getId()))
				.setUser((dbo.getUser() == null ? null : dbo.getUser().getId()));
	}

	@Override
	public List<ListPosition> toModel(List<ListPositionDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}