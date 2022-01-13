package de.ollie.shoppinglist.core.service.port.persistence;

import java.util.Optional;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.Shop;
import lombok.Generated;

/**
 * A generated persistence port interface for Shop CRUD operations.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface ShopGeneratedPersistencePort {

	Shop create(Shop model);

	Page<Shop> findAll(PageParameters pageParameters);

	Optional<Shop> findById(Long id);

	Shop update(Shop model);

	void delete(Shop model);

}