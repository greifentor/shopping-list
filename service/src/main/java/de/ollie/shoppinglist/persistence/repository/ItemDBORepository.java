package de.ollie.shoppinglist.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ollie.shoppinglist.persistence.entity.ItemDBO;

/**
 * A JPA repository for items.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Repository
public interface ItemDBORepository extends JpaRepository<ItemDBO, Long> {
}