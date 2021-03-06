package edu.avans.hartigehap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Owner;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.repository.OwnerRepository;
import edu.avans.hartigehap.service.OwnerService;

@Service("ownerService")
@Repository
@Transactional(rollbackFor = StateException.class)
public class OwnerSeviceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public List<Owner> findAll() {
        return (List<Owner>) ownerRepository.findAll();
    }

    @Override
    public Owner findById(long id) {
        return null;
    }

    @Override
    public List<Owner> findByName(String name) {
        return ownerRepository.findByName(name);
    }

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void delete(Owner owner) {
        ownerRepository.delete(owner);
    }
}
