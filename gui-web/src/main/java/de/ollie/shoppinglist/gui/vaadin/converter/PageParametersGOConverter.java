package de.ollie.shoppinglist.gui.vaadin.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.gui.vaadin.go.converter.PageParametersGO;

/**
 * A converter for the page parameters GO.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class PageParametersGOConverter {

	public PageParameters toModel(PageParametersGO go) {
		if (go == null) {
			return null;
		}
		return new PageParameters().setEntriesPerPage(go.getEntriesPerPage()).setPageNumber(go.getPageNumber());
	}

}