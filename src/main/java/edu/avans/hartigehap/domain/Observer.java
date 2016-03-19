package edu.avans.hartigehap.domain;

public interface Observer<T> {
    void notify(T object);
}