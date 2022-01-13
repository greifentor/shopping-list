package de.ollie.shoppinglist.core.service;

import java.util.Optional;

import de.ollie.shoppinglist.core.model.User;

/**
 * A service interface for User management.
 */
public interface UserService extends UserGeneratedService {

	Optional<User> findByGlobalId(String globalId);

}