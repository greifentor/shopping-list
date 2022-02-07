package de.ollie.shoppinglist.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ollie.shoppinglist.persistence.entity.ShopDBO;
import lombok.Generated;
import java.util.List;

import de.ollie.shoppinglist.persistence.entity.UserDBO;

/**
 * A generated JPA repository for shops.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Repository
public interface ShopGeneratedDBORepository extends JpaRepository<ShopDBO, Long> {

	List<ShopDBO> findAllByUser(UserDBO user);

}