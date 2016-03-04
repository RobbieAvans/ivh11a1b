package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.Hall;

public interface HallService {
    List<Hall> findAll();

    Hall findById(long id);

    Hall save(Hall hall);

    boolean deleteById(long id);

    boolean delete(Hall hall);
}
