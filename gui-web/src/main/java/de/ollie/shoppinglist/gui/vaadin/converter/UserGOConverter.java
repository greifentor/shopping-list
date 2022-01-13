package de.ollie.shoppinglist.gui.vaadin.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.shoppinglist.gui.vaadin.go.UserGO;
import de.ollie.shoppinglist.core.model.User;

/**
 * A GO converter for users.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class UserGOConverter implements ToGOConverter<UserGO, User> {

	public UserGO toGO(User model) {
		if (model == null) {
			return null;
		}
		return new UserGO()
				.setId(model.getId())
				.setGlobalId(model.getGlobalId())
				.setName(model.getName())
				.setToken(model.getToken());
	}

	public User toModel(UserGO go) {
		if (go == null) {
			return null;
		}
		return new User()
				.setId(go.getId())
				.setGlobalId(go.getGlobalId())
				.setName(go.getName())
				.setToken(go.getToken());
	}

}