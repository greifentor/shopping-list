package de.ollie.shoppinglist.gui.event;

import static de.ollie.shoppinglist.util.Check.ensure;

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Identifiers for event types.
 *
 * @author ollie (05.01.2022)
 */
@EqualsAndHashCode
@Getter
@ToString
public class EventType {

	private static final Map<String, String> NAMES = new HashMap<>();

	public static final EventType LOGGED_IN = new EventType("LOGGED_IN");
	public static final EventType LOGGED_OUT = new EventType("LOGGED_OUT");

	private String name;

	public EventType(String name) {
		super();
		ensure(name != null, new NullPointerException("name cannot be null."));
		ensure(!name.isEmpty(), "name cannot be empty.");
		if (NAMES.containsKey(name)) {
			String clsName = NAMES.get(name);
			throw new IllegalArgumentException(
					"EventType with name '" + name + "' already created in class '" + clsName + "'!");
		}
		NAMES.put(name, getClass().getName());
		this.name = name;
	}

}