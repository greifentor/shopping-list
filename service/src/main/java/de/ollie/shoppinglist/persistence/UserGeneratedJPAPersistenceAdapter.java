package de.ollie.shoppinglist.persistence;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.shoppinglist.core.model.Page;
import de.ollie.shoppinglist.core.model.PageParameters;
import de.ollie.shoppinglist.core.model.User;
import de.ollie.shoppinglist.core.service.port.persistence.UserPersistencePort;
import de.ollie.shoppinglist.persistence.converter.PageConverter;
import de.ollie.shoppinglist.persistence.converter.PageParametersToPageableConverter;
import de.ollie.shoppinglist.persistence.converter.UserDBOConverter;
import de.ollie.shoppinglist.persistence.entity.UserDBO;
import de.ollie.shoppinglist.persistence.repository.UserDBORepository;
import lombok.Generated;

/**
 * A generated JPA persistence adapter for users.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class UserGeneratedJPAPersistenceAdapter implements UserPersistencePort {

	@Inject
	protected UserDBOConverter converter;
	@Inject
	protected UserDBORepository repository;

	@Inject
	protected PageParametersToPageableConverter pageParametersToPageableConverter;

	protected PageConverter<User, UserDBO> pageConverter;

	@PostConstruct
	public void postConstruct() {
		pageConverter = new PageConverter<>(converter);
	}

	@Override
	public User create(User model) {
		model.setId(-1);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public List<User> findAll() {
		return converter.toModel(repository.findAll());
	}

	@Override
	public Page<User> findAll(PageParameters pageParameters) {
		return pageConverter.convert(repository.findAll(pageParametersToPageableConverter.convert(pageParameters)));
	}

	@Override
	public Optional<User> findById(Long id) {
		return repository.findById(id).map(dbo -> converter.toModel(dbo));
	}

	@Override
	public User update(User model) {
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public void delete(User model) {
		repository.deleteById(model.getId());
	}

	@Override
	public Optional<User> findByGlobalId(String globalId) {
		return Optional.ofNullable(converter.toModel(repository.findByGlobalId(globalId).orElse(null)));
	}

}