package de.ollie.shoppinglist.core.service;

import java.time.LocalDateTime;

import de.ollie.shoppinglist.core.service.exception.JWTNotValidException;
import lombok.Value;

/**
 * @author ollie (05.01.2022)
 */
public interface JWTService {

	@Value
	public static class AuthorizationData {
		private String userName;
		private String applicationName;
		private LocalDateTime endOfValidity;
		private String[] userRights;
	}

	/**
	 * Returns the neccessary authentication data from the passed JWT.
	 * 
	 * @param jwt The JWT whose data are to return.
	 * @throws JWTNotValidException If the JWT could not be verified.
	 */
	AuthorizationData getAuthorizationData(String jwt);

}