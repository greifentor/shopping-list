package de.ollie.shoppinglist.core.service.impl;

import javax.inject.Named;

import de.ollie.shoppinglist.core.model.Status;
import de.ollie.shoppinglist.core.service.StatusService;

@Named
public class StatusServiceImpl implements StatusService {

	@Override
	public Status getStatus() {
		return new Status().setFreeMem(Runtime.getRuntime().freeMemory());
	}

}
