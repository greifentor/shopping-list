package de.ollie.shoppinglist.core.service;

import java.util.List;

import de.ollie.shoppinglist.core.model.ListPosition;
import de.ollie.shoppinglist.core.model.Shop;

/**
 * A service interface for ListPosition management.
 */
public interface ListPositionService extends ListPositionGeneratedService {

	long countByShop(Shop shop);

	List<ListPosition> findAllByShop(Shop shop);

}