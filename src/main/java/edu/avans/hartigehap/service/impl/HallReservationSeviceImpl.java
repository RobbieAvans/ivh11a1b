package edu.avans.hartigehap.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.repository.*;
import edu.avans.hartigehap.service.*;
import edu.avans.hartigehap.domain.*;

@Service("hallReservationService")
@Repository
@Transactional(rollbackFor = StateException.class)
public class HallReservationSeviceImpl implements HallReservationService {

    @Autowired
    private HallReservationRepository hallReservationRepository;
   
	@Override
	public List<HallReservation> findAll() {
		return (List<HallReservation>)hallReservationRepository.findAll();
	}

	@Override
	public HallReservation save(HallReservation hallReservation) {
		return hallReservationRepository.save(hallReservation);
	}

	@Override
	public void delete(HallReservation hallReservation) {
		hallReservationRepository.delete(hallReservation);
	}
}
