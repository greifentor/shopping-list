package de.ollie.shoppinglist.core.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A container for user login id's.
 *
 * @author ollie (05.01.2022)
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class UserLoginIdSO {

	private String key;

}