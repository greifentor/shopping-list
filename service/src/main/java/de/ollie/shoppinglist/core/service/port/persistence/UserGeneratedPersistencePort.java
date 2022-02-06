package de.ollie.shoppinglist.core.service.port.persistence;

import java.util.List;
import java.util.Optional;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.User;
import lombok.Generated;

/**
 * A generated persistence port interface for User CRUD operations.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface UserGeneratedPersistencePort {

	User create(User model);

	List<User> findAll();

	Page<User> findAll(PageParameters pageParameters);

	Optional<User> findById(Long id);

	User update(User model);

	void delete(User model);

	Optional<User> findByGlobalId(String globalId);

}