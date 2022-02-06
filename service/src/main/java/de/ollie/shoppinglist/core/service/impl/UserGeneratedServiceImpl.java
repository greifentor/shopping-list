package de.ollie.shoppinglist.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.User;
import de.ollie.shoppinglist.core.service.port.persistence.UserPersistencePort;
import de.ollie.shoppinglist.core.service.UserService;
import lombok.Generated;

/**
 * A generated service interface implementation for User management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class UserGeneratedServiceImpl implements UserService {

	@Inject
	protected UserPersistencePort persistencePort;

	@Override
	public User create(User model) {
		return persistencePort.create(model);
	}

	@Override
	public List<User> findAll() {
		return persistencePort.findAll();
	}

	@Override
	public Page<User> findAll(PageParameters pageParameters) {
		return persistencePort.findAll(pageParameters);
	}

	@Override
	public Optional<User> findById(Long id) {
		return persistencePort.findById(id);
	}

	@Override
	public User update(User model) {
		return persistencePort.update(model);
	}

	@Override
	public void delete(User model) {
		persistencePort.delete(model);
	}

	@Override
	public Optional<User> findByGlobalId(String globalId) {
		return persistencePort.findByGlobalId(globalId);
	}

}