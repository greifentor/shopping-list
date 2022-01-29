package de.ollie.shoppinglist.rest.v1.converter;

import javax.inject.Named;

import de.ollie.shoppinglist.core.model.Status;
import de.ollie.shoppinglist.rest.v1.dto.StatusDTO;

@Named
public class StatusDTOConverter {

	public StatusDTO toDTO(Status model) {
		if (model == null) {
		return null;
		}
		return new StatusDTO().setFreeMem(model.getFreeMem());
	}

}
