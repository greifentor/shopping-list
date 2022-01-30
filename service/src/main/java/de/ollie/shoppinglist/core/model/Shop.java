package de.ollie.shoppinglist.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A model for shops.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public class Shop {

	private long id;
	private User user;
	private String name;
	private int sortOrder;

}