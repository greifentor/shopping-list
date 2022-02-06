package de.ollie.shoppinglist.core.service;

import java.util.List;
import java.util.Optional;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.ListPosition;
import lombok.Generated;

import de.ollie.shoppinglist.core.model.Shop;

/**
 * A generated service interface for ListPosition management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface ListPositionGeneratedService {

	ListPosition create(ListPosition model);

	List<ListPosition> findAll();

	Page<ListPosition> findAll(PageParameters pageParameters);

	Optional<ListPosition> findById(Long id);

	ListPosition update(ListPosition model);

	void delete(ListPosition model);

	List<ListPosition> findAllByShop(Shop shop);

}