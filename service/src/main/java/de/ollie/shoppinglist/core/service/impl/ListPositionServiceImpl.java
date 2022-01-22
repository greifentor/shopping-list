package de.ollie.shoppinglist.core.service.impl;

import java.util.List;

import javax.inject.Named;

import de.ollie.shoppinglist.core.model.ListPosition;
import de.ollie.shoppinglist.core.model.Shop;

/**
 * A service interface implementation for ListPosition management.
 */
@Named
public class ListPositionServiceImpl extends ListPositionGeneratedServiceImpl {

	@Override
	public long countByShop(Shop shop) {
		return persistencePort.countByShop(shop);
	}

	@Override
	public List<ListPosition> findAllByShop(Shop shop) {
		return persistencePort.findAllByShop(shop);
	}

}