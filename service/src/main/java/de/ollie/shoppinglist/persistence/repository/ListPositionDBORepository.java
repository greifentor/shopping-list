package de.ollie.shoppinglist.persistence.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import de.ollie.shoppinglist.persistence.entity.ListPositionDBO;
import de.ollie.shoppinglist.persistence.entity.ShopDBO;

/**
 * A JPA repository for list_positions.
 */
@Repository
public interface ListPositionDBORepository extends ListPositionGeneratedDBORepository {

	long countByShop(ShopDBO shop);

	List<ListPositionDBO> findAllByShop(ShopDBO shop);

}