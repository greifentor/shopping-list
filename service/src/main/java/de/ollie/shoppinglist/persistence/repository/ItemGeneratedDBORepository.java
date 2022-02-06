package de.ollie.shoppinglist.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ollie.shoppinglist.persistence.entity.ItemDBO;
import lombok.Generated;
import java.util.List;

import de.ollie.shoppinglist.persistence.entity.UserDBO;

/**
 * A generated JPA repository for items.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Repository
public interface ItemGeneratedDBORepository extends JpaRepository<ItemDBO, Long> {

	List<ItemDBO> findAllByUser(UserDBO user);

}