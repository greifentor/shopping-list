package de.ollie.shoppinglist.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A model for list_positions.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public class ListPosition {

	private long id;
	private Item item;
	private Shop shop;
	private User user;

}