package de.ollie.shoppinglist.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import de.ollie.shoppinglist.persistence.entity.UserDBO;
import de.ollie.shoppinglist.persistence.repository.UserDBORepository;

@Disabled("OLI: It's for general database behavior tests only.")
@DataJpaTest
class DAOExceptionTest {

	private static final String GLOBAL_ID = "global-id";
	private static final String TOKEN = "token";

	@Inject
	private UserDBORepository repository;

	@Test
	void notNullIntegrityCheck() {
		// Prepare
		UserDBO dbo = new UserDBO().setGlobalId(GLOBAL_ID).setName(null).setToken(TOKEN);
		// Run
		DataIntegrityViolationException e =
				assertThrows(DataIntegrityViolationException.class, () -> repository.save(dbo));
		System.out.println("\n\n>>>>> " + e.getMostSpecificCause());
		e.printStackTrace();
	}

}