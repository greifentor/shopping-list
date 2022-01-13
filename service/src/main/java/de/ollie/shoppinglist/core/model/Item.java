package de.ollie.shoppinglist.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A model for items.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public class Item {

	private long id;
	private long shop;
	private Long user;
	private String name;

}