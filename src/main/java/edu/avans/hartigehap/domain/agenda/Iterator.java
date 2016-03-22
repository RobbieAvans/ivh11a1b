package edu.avans.hartigehap.domain.agenda;

public interface Iterator<T> {

    /**
     * Check if there is a next item
     * 
     * @return boolean
     */
    public boolean hasNext();

    /**
     * Get the next item
     * 
     * @return T
     */
    public T next();
}
