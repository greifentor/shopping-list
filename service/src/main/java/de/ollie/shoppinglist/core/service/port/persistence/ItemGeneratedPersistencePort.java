package de.ollie.shoppinglist.core.service.port.persistence;

import java.util.Optional;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.Item;
import lombok.Generated;

/**
 * A generated persistence port interface for Item CRUD operations.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface ItemGeneratedPersistencePort {

	Item create(Item model);

	Page<Item> findAll(PageParameters pageParameters);

	Optional<Item> findById(Long id);

	Item update(Item model);

	void delete(Item model);

}