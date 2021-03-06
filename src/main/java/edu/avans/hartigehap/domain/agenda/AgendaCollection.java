package edu.avans.hartigehap.domain.agenda;

import java.util.ArrayList;
import java.util.List;

public class AgendaCollection {

    /**
     * Agenda Items
     */
    private List<AgendaItem> items;

    public AgendaCollection() {
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
    public AgendaCollection addItem(AgendaItem item) {
        items.add(item);

        // Return this for chaining
        return this;
    }

    /**
     * Create an iterator for the agenda
     * 
     * @return Iterator
     */
    public Iterator<AgendaItem> createIterator() {
        return new AgendaIterator(items.toArray(new AgendaItem[items.size()]));
    }
}
