package de.ollie.shoppinglist.core.service;

import de.ollie.shoppinglist.core.model.Status;

public interface StatusService {
	/**
	 * Returns a status DTO with current server data.
	 */
	Status getStatus();

}
