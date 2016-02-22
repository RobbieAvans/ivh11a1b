package edu.avans.hartigehap.domain.agenda;

public interface Iterator {
	
	/**
	 * Check if there is a next item
	 * 
	 * @return boolean
	 */
	public boolean hasNext();
	
	/**
	 * Get the next item
	 * 
	 * @return Object
	 */
	public Object next();
}
