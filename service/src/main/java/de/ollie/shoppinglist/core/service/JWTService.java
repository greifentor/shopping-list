package de.ollie.shoppinglist.core.service;

import java.time.LocalDateTime;

import de.ollie.shoppinglist.core.model.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.Accessors;

public interface JWTService {

	@Accessors(chain = true)
	@AllArgsConstructor
	@EqualsAndHashCode
	@Getter
	@Generated
	public static class AuthorizationData {

		private String applicationName;
		private LocalDateTime endOfValidity;
		private User user;
		private String[] applicationRights;

	}

	AuthorizationData getAuthorizationData(String jwt);

}
