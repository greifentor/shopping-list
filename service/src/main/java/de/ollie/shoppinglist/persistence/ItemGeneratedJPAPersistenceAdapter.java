package de.ollie.shoppinglist.persistence;

import static de.ollie.shoppinglist.util.Check.ensure;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.Item;
import de.ollie.shoppinglist.core.service.exception.NotNullConstraintViolationException;
import de.ollie.shoppinglist.core.service.port.persistence.ItemPersistencePort;
import de.ollie.shoppinglist.persistence.converter.PageConverter;
import de.ollie.shoppinglist.persistence.converter.PageParametersToPageableConverter;
import de.ollie.shoppinglist.persistence.converter.ItemDBOConverter;
import de.ollie.shoppinglist.persistence.entity.ItemDBO;
import de.ollie.shoppinglist.persistence.repository.ItemDBORepository;
import de.ollie.shoppinglist.persistence.converter.UserDBOConverter;
import lombok.Generated;

import de.ollie.shoppinglist.core.model.User;

/**
 * A generated JPA persistence adapter for items.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class ItemGeneratedJPAPersistenceAdapter implements ItemPersistencePort {

	@Inject
	protected ItemDBOConverter converter;
	@Inject
	protected ItemDBORepository repository;
	@Inject
	protected UserDBOConverter userDBOConverter;

	@Inject
	protected PageParametersToPageableConverter pageParametersToPageableConverter;

	protected PageConverter<Item, ItemDBO> pageConverter;

	@PostConstruct
	public void postConstruct() {
		pageConverter = new PageConverter<>(converter);
	}

	@Override
	public Item create(Item model) {
		model.setId(-1);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public List<Item> findAll() {
		return converter.toModel(repository.findAll());
	}

	@Override
	public Page<Item> findAll(PageParameters pageParameters) {
		return pageConverter.convert(repository.findAll(pageParametersToPageableConverter.convert(pageParameters)));
	}

	@Override
	public Optional<Item> findById(Long id) {
		return repository.findById(id).map(dbo -> converter.toModel(dbo));
	}

	@Override
	public Item update(Item model) {
		ensure(
				model.getShop() != null,
				() -> new NotNullConstraintViolationException("Item field shop cannot be null.", "Item", "shop"));
		ensure(
				model.getName() != null,
				() -> new NotNullConstraintViolationException("Item field name cannot be null.", "Item", "name"));
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public void delete(Item model) {
		repository.deleteById(model.getId());
	}

	@Override
	public List<Item> findAllByUser(User user) {
		return converter.toModel(repository.findAllByUser(userDBOConverter.toDBO(user)));
	}

}