package edu.avans.hartigehap.domain.agenda;

public class AgendaIterator implements Iterator {

	private AgendaItem[] items;
	private int position;

	public AgendaIterator(AgendaItem[] items) {
		this.position = 0;
		this.items = items;
	}

	@Override
	public boolean hasNext() {
		return position < items.length;
	}

	@Override
	public AgendaItem next() {
		AgendaItem agendaItem = items[position];
		position++;

		return agendaItem;
	}

}
