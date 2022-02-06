package de.ollie.shoppinglist.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ollie.shoppinglist.persistence.entity.ListPositionDBO;
import de.ollie.shoppinglist.persistence.entity.ShopDBO;
import lombok.Generated;
import java.util.List;

/**
 * A generated JPA repository for list_positions.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Repository
public interface ListPositionGeneratedDBORepository extends JpaRepository<ListPositionDBO, Long> {

	List<ListPositionDBO> findAllByShop(ShopDBO shop);

}