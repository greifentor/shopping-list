package de.ollie.shoppinglist.core.service.port.persistence;

import java.util.Optional;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.ListPosition;
import lombok.Generated;

/**
 * A generated persistence port interface for ListPosition CRUD operations.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface ListPositionGeneratedPersistencePort {

	ListPosition create(ListPosition model);

	Page<ListPosition> findAll(PageParameters pageParameters);

	Optional<ListPosition> findById(Long id);

	ListPosition update(ListPosition model);

	void delete(ListPosition model);

}