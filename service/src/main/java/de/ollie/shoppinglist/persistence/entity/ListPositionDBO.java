package de.ollie.shoppinglist.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A DBO for list_positions.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
@Entity(name = "ListPosition")
@Table(name = "LIST_POSITION")
public class ListPositionDBO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;
	@JoinColumn(name = "ITEM", nullable = false, referencedColumnName = "ID")
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	private ItemDBO item;
	@JoinColumn(name = "SHOP", nullable = false, referencedColumnName = "ID")
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	private ShopDBO shop;
	@JoinColumn(name = "USER", nullable = false, referencedColumnName = "ID")
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	private UserDBO user;

}