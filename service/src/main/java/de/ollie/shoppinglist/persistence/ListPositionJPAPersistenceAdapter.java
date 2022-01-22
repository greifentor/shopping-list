package de.ollie.shoppinglist.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import de.ollie.shoppinglist.core.model.ListPosition;
import de.ollie.shoppinglist.core.model.Shop;

/**
 * A JPA persistence adapter for list_positions.
 */
@Named
public class ListPositionJPAPersistenceAdapter extends ListPositionGeneratedJPAPersistenceAdapter {

	@Override
	public long countByShop(Shop shop) {
		if (shop == null) {
			return 0;
		}
		return repository.countByShop(shop.getId());
	}

	@Override
	public List<ListPosition> findAllByShop(Shop shop) {
		if (shop == null) {
			return new ArrayList<>();
		}
		return converter.toModel(repository.findAllByShop(shop.getId()));
	}

}