package de.ollie.shoppinglist.core.service;

import java.util.List;
import java.util.Optional;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.Item;
import lombok.Generated;

import de.ollie.shoppinglist.core.model.User;

/**
 * A generated service interface for Item management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface ItemGeneratedService {

	Item create(Item model);

	List<Item> findAll();

	Page<Item> findAll(PageParameters pageParameters);

	Optional<Item> findById(Long id);

	Item update(Item model);

	void delete(Item model);

	List<Item> findAllByUser(User user);

}