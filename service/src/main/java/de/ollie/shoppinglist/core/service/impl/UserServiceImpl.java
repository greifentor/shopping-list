package de.ollie.shoppinglist.core.service.impl;

import java.util.Optional;

import javax.inject.Named;

import de.ollie.shoppinglist.core.model.User;

/**
 * A service interface implementation for User management.
 */
@Named
public class UserServiceImpl extends UserGeneratedServiceImpl {

	@Override
	public Optional<User> findByGlobalId(String globalId) {
		return persistencePort.findByGlobalId(globalId);
	}

}