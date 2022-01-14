package de.ollie.shoppinglist.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.Item;
import de.ollie.shoppinglist.core.service.port.persistence.ItemPersistencePort;
import de.ollie.shoppinglist.core.service.ItemService;
import lombok.Generated;

/**
 * A generated service interface implementation for Item management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class ItemGeneratedServiceImpl implements ItemService {

	@Inject
	protected ItemPersistencePort persistencePort;

	@Override
	public Item create(Item model) {
		return persistencePort.create(model);
	}

	@Override
	public List<Item> findAll() {
		return persistencePort.findAll();
	}

	@Override
	public Page<Item> findAll(PageParameters pageParameters) {
		return persistencePort.findAll(pageParameters);
	}

	@Override
	public Optional<Item> findById(Long id) {
		return persistencePort.findById(id);
	}

	@Override
	public Item update(Item model) {
		return persistencePort.update(model);
	}

	@Override
	public void delete(Item model) {
		persistencePort.delete(model);
	}

}