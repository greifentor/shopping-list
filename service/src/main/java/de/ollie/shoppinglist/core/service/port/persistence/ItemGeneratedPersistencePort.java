package de.ollie.shoppinglist.core.service.port.persistence;

import java.util.List;
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

	List<Item> findAll();

	Page<Item> findAll(PageParameters pageParameters);

	Optional<Item> findById(Long id);

	Item update(Item model);

	void delete(Item model);

}