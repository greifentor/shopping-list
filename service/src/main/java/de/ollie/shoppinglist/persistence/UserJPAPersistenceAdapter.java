package de.ollie.shoppinglist.persistence;

import java.util.Optional;

import javax.inject.Named;

import de.ollie.shoppinglist.core.model.User;

/**
 * A JPA persistence adapter for users.
 */
@Named
public class UserJPAPersistenceAdapter extends UserGeneratedJPAPersistenceAdapter {

	@Override
	public Optional<User> findByGlobalId(String globalId) {
		return Optional
				.ofNullable(
						converter
								.toModel(
										repository
												.findAll()
												.stream()
												.filter(user -> user.getGlobalId().equals(globalId))
												.findFirst()
												.orElse(null)));
	}

}