package de.ollie.shoppinglist.persistence;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.core.service.port.persistence.ShopPersistencePort;
import de.ollie.shoppinglist.persistence.converter.PageConverter;
import de.ollie.shoppinglist.persistence.converter.PageParametersToPageableConverter;
import de.ollie.shoppinglist.persistence.converter.ShopDBOConverter;
import de.ollie.shoppinglist.persistence.entity.ShopDBO;
import de.ollie.shoppinglist.persistence.repository.ShopDBORepository;
import lombok.Generated;

/**
 * A generated JPA persistence adapter for shops.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class ShopGeneratedJPAPersistenceAdapter implements ShopPersistencePort {

	@Inject
	protected ShopDBOConverter converter;
	@Inject
	protected ShopDBORepository repository;

	@Inject
	protected PageParametersToPageableConverter pageParametersToPageableConverter;

	protected PageConverter<Shop, ShopDBO> pageConverter;

	@PostConstruct
	public void postConstruct() {
		pageConverter = new PageConverter<>(converter);
	}

	@Override
	public Shop create(Shop model) {
		model.setId(-1);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public List<Shop> findAll() {
		return converter.toModel(repository.findAll());
	}

	@Override
	public Page<Shop> findAll(PageParameters pageParameters) {
		return pageConverter.convert(repository.findAll(pageParametersToPageableConverter.convert(pageParameters)));
	}

	@Override
	public Optional<Shop> findById(Long id) {
		return repository.findById(id).map(dbo -> converter.toModel(dbo));
	}

	@Override
	public Shop update(Shop model) {
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public void delete(Shop model) {
		repository.deleteById(model.getId());
	}

}