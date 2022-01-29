package de.ollie.shoppinglist.core.service;

import java.time.LocalDateTime;

import de.ollie.shoppinglist.core.model.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.Accessors;

public interface JWTService {

	static final String CLAIM_NAME_APPLICATION_NAME = "applicationName";
	static final String CLAIM_NAME_APPLICATION_RIGHTS = "applicationRights";
	static final String CLAIM_NAME_LOGIN_DATE = "loginDate";
	static final String CLAIM_NAME_USER_GLOBAL_ID = "userGlobalId";
	static final String CLAIM_NAME_USER_NAME = "userName";
	static final String CLAIM_NAME_USER_TOKEN = "userToken";

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

	LocalDateTime getLoginDate(String jwt);

}
