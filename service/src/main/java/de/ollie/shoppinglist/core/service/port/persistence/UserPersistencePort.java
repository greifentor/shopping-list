package de.ollie.shoppinglist.core.service.port.persistence;

import java.util.Optional;

import de.ollie.shoppinglist.core.model.User;

/**
 * A persistence port interface for User CRUD operations.
 */
public interface UserPersistencePort extends UserGeneratedPersistencePort {

	Optional<User> findByGlobalId(String globalId);

}