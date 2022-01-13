package de.ollie.shoppinglist.core.service.impl;

import java.util.Optional;

import javax.inject.Inject;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.core.service.port.persistence.ShopPersistencePort;
import de.ollie.shoppinglist.core.service.ShopService;
import lombok.Generated;

/**
 * A generated service interface implementation for Shop management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class ShopGeneratedServiceImpl implements ShopService {

	@Inject
	protected ShopPersistencePort persistencePort;

	@Override
	public Shop create(Shop model) {
		return persistencePort.create(model);
	}

	@Override
	public Page<Shop> findAll(PageParameters pageParameters) {
		return persistencePort.findAll(pageParameters);
	}

	@Override
	public Optional<Shop> findById(Long id) {
		return persistencePort.findById(id);
	}

	@Override
	public Shop update(Shop model) {
		return persistencePort.update(model);
	}

	@Override
	public void delete(Shop model) {
		persistencePort.delete(model);
	}

}