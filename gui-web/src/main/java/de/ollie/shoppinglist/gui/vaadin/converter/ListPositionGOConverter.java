package de.ollie.shoppinglist.gui.vaadin.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.shoppinglist.gui.vaadin.go.ListPositionGO;
import de.ollie.shoppinglist.core.model.ListPosition;

/**
 * A GO converter for list_positions.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class ListPositionGOConverter implements ToGOConverter<ListPositionGO, ListPosition> {

	@Override
	public ListPositionGO toGO(ListPosition model) {
		if (model == null) {
			return null;
		}
		return new ListPositionGO()
				.setId(model.getId())
				.setItem(model.getItem())
				.setShop(model.getShop())
				.setUser(model.getUser());
	}

	public ListPosition toModel(ListPositionGO go) {
		if (go == null) {
			return null;
		}
		return new ListPosition()
				.setId(go.getId())
				.setItem(go.getItem())
				.setShop(go.getShop())
				.setUser(go.getUser());
	}

}