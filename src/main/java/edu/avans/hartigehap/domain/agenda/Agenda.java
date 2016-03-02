package edu.avans.hartigehap.domain.agenda;

import java.util.ArrayList;
import java.util.List;

public class Agenda {

	/**
	 * Agenda Items
	 */
	private List<AgendaItem> items;

	public Agenda() {
		this.items = new ArrayList<AgendaItem>();
	}

	/**
	 * Add an item to the agenda
	 * 
	 * Builder Pattern
	 * 
	 * @param item
	 * @return Agenda
	 */
	public Agenda addItem(AgendaItem item) {
		items.add(item);

		// Return this for chaining
		return this;
	}

	/**
	 * Create an iterator for the agenda
	 * 
	 * @return Iterator
	 */
	public Iterator createIterator() {
		return new AgendaIterator(items.toArray(new AgendaItem[items.size()]));
	}
}
