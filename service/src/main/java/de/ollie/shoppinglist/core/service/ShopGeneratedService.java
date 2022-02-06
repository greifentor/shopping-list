package de.ollie.shoppinglist.core.service;

import java.util.List;
import java.util.Optional;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.Shop;
import lombok.Generated;

import de.ollie.shoppinglist.core.model.User;

/**
 * A generated service interface for Shop management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface ShopGeneratedService {

	Shop create(Shop model);

	List<Shop> findAll();

	Page<Shop> findAll(PageParameters pageParameters);

	Optional<Shop> findById(Long id);

	Shop update(Shop model);

	void delete(Shop model);

	List<Shop> findAllByUser(User user);

}