package edu.avans.hartigehap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.repository.HallRepository;
import edu.avans.hartigehap.service.HallService;

@Service("hallService")
@Repository
@Transactional(rollbackFor = StateException.class)
public class HallServiceImpl implements HallService {

	@Autowired
    private HallRepository hallRepository;
	
	@Override
	public List<Hall> findAll() {
		return (List<Hall>)hallRepository.findAll();
	}

	@Override
	public Hall findById(long id) {
		return hallRepository.findOne(id);
	}

	@Override
	public Hall save(Hall hall) {
		return hallRepository.save(hall);
	}

	@Override
	public void deleteById(long hallId) {
		Hall hall = hallRepository.findOne(hallId);
		
		if (hall != null) {
			delete(hall);
		}
	}
	
	@Override
	public void delete(Hall hall) {
		hallRepository.delete(hall);
	}

}
