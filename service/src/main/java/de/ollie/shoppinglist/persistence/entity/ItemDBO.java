package de.ollie.shoppinglist.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A DBO for items.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
@Entity(name = "Item")
@Table(name = "ITEM")
public class ItemDBO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;
	@Column(name = "SHOP", nullable = false)
	private long shop;
	@Column(name = "USER")
	private Long user;
	@Column(name = "NAME", nullable = false)
	private String name;
	@Column(name = "POSITION", nullable = false)
	private int position;

}