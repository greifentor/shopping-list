package de.ollie.shoppinglist.core.service.port.persistence;

import java.util.List;

import de.ollie.shoppinglist.core.model.ListPosition;
import de.ollie.shoppinglist.core.model.Shop;

/**
 * A persistence port interface for ListPosition CRUD operations.
 */
public interface ListPositionPersistencePort extends ListPositionGeneratedPersistencePort {

	long countByShop(Shop shop);

	List<ListPosition> findAllByShop(Shop shop);

}