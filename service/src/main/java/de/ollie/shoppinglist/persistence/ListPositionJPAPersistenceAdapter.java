package de.ollie.shoppinglist.persistence;

import javax.inject.Named;

import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.persistence.repository.ShopDBORepository;
import lombok.RequiredArgsConstructor;

/**
 * A JPA persistence adapter for list_positions.
 */
@Named
@RequiredArgsConstructor
public class ListPositionJPAPersistenceAdapter extends ListPositionGeneratedJPAPersistenceAdapter {

	private final ShopDBORepository shopDBORepository;

	@Override
	public long countByShop(Shop shop) {
		if (shop == null) {
			return 0;
		}
		return repository.countByShop(shopDBORepository.findById(shop.getId()).orElse(null));
	}

}