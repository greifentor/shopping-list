package de.ollie.shoppinglist.persistence.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.shoppinglist.persistence.entity.UserDBO;
import de.ollie.shoppinglist.core.model.User;

/**
 * A DBO converter for users.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class UserDBOConverter implements ToModelConverter<User, UserDBO> {

	public UserDBO toDBO(User model) {
		if (model == null) {
			return null;
		}
		return new UserDBO()
				.setId(model.getId())
				.setGlobalId(model.getGlobalId())
				.setName(model.getName())
				.setToken(model.getToken());
	}

	@Override
	public User toModel(UserDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new User()
				.setId(dbo.getId())
				.setGlobalId(dbo.getGlobalId())
				.setName(dbo.getName())
				.setToken(dbo.getToken());
	}

}