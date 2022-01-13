package de.ollie.shoppinglist.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ollie.shoppinglist.persistence.entity.UserDBO;

/**
 * A JPA repository for users.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Repository
public interface UserDBORepository extends JpaRepository<UserDBO, Long> {
}