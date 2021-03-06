package de.ollie.shoppinglist.persistence;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.ListPosition;
import de.ollie.shoppinglist.core.service.port.persistence.ListPositionPersistencePort;
import de.ollie.shoppinglist.persistence.converter.PageConverter;
import de.ollie.shoppinglist.persistence.converter.PageParametersToPageableConverter;
import de.ollie.shoppinglist.persistence.converter.ListPositionDBOConverter;
import de.ollie.shoppinglist.persistence.entity.ListPositionDBO;
import de.ollie.shoppinglist.persistence.repository.ListPositionDBORepository;
import lombok.Generated;

/**
 * A generated JPA persistence adapter for list_positions.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class ListPositionGeneratedJPAPersistenceAdapter implements ListPositionPersistencePort {

	@Inject
	protected ListPositionDBOConverter converter;
	@Inject
	protected ListPositionDBORepository repository;

	@Inject
	protected PageParametersToPageableConverter pageParametersToPageableConverter;

	protected PageConverter<ListPosition, ListPositionDBO> pageConverter;

	@PostConstruct
	public void postConstruct() {
		pageConverter = new PageConverter<>(converter);
	}

	@Override
	public ListPosition create(ListPosition model) {
		model.setId(-1);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public List<ListPosition> findAll() {
		return converter.toModel(repository.findAll());
	}

	@Override
	public Page<ListPosition> findAll(PageParameters pageParameters) {
		return pageConverter.convert(repository.findAll(pageParametersToPageableConverter.convert(pageParameters)));
	}

	@Override
	public Optional<ListPosition> findById(Long id) {
		return repository.findById(id).map(dbo -> converter.toModel(dbo));
	}

	@Override
	public ListPosition update(ListPosition model) {
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public void delete(ListPosition model) {
		repository.deleteById(model.getId());
	}

}