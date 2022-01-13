package de.ollie.shoppinglist.core.service;

import java.util.Optional;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.Item;
import lombok.Generated;

/**
 * A generated service interface for Item management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface ItemGeneratedService {

	Item create(Item model);

	Page<Item> findAll(PageParameters pageParameters);

	Optional<Item> findById(Long id);

	Item update(Item model);

	void delete(Item model);

}