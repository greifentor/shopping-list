package de.ollie.shoppinglist.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ollie.shoppinglist.persistence.entity.ListPositionDBO;

/**
 * A JPA repository for list_positions.
 */
@Repository
public interface ListPositionDBORepository extends JpaRepository<ListPositionDBO, Long> {

	long countByShop(long shopId);

	List<ListPositionDBO> findAllByShop(long shopId);

}