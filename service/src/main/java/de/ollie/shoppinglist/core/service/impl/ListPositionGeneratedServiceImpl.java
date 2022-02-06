package de.ollie.shoppinglist.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.ListPosition;
import de.ollie.shoppinglist.core.service.port.persistence.ListPositionPersistencePort;
import de.ollie.shoppinglist.core.service.ListPositionService;
import lombok.Generated;

import de.ollie.shoppinglist.core.model.Shop;

/**
 * A generated service interface implementation for ListPosition management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class ListPositionGeneratedServiceImpl implements ListPositionService {

	@Inject
	protected ListPositionPersistencePort persistencePort;

	@Override
	public ListPosition create(ListPosition model) {
		return persistencePort.create(model);
	}

	@Override
	public List<ListPosition> findAll() {
		return persistencePort.findAll();
	}

	@Override
	public Page<ListPosition> findAll(PageParameters pageParameters) {
		return persistencePort.findAll(pageParameters);
	}

	@Override
	public Optional<ListPosition> findById(Long id) {
		return persistencePort.findById(id);
	}

	@Override
	public ListPosition update(ListPosition model) {
		return persistencePort.update(model);
	}

	@Override
	public void delete(ListPosition model) {
		persistencePort.delete(model);
	}

	@Override
	public List<ListPosition> findAllByShop(Shop shop) {
		return persistencePort.findAllByShop(shop);
	}

}