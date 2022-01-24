package de.ollie.shoppinglist.gui.vaadin.go;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A container for items data in the GUI layer.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public class ItemGO {

	private long id;
	private long shop;
	private long user;
	private String name;
	private int position;

}