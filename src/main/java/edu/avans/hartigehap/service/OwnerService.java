package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.Owner;

public interface OwnerService {
    List<Owner> findAll();

    Owner findById(long id);

    List<Owner> findByName(String name);

    Owner save(Owner owner);

    void delete(Owner owner);
}
