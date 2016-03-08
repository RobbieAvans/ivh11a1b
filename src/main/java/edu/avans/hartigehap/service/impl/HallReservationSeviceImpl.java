package edu.avans.hartigehap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallReservation;
import edu.avans.hartigehap.domain.HallReservationOption;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.repository.HallReservationRepository;
import edu.avans.hartigehap.service.HallReservationService;
import edu.avans.hartigehap.service.HallService;

@Service("hallReservationService")
@Repository
@Transactional(rollbackFor = StateException.class)
public class HallReservationSeviceImpl implements HallReservationService {

    @Autowired
    private HallReservationRepository hallReservationRepository;
    
    @Autowired
    private HallService hallService;

   
	@Override
	public List<HallReservation> findAll() {
        List<Hall> halls = hallService.findAll();
        List<HallReservation> reservations = new ArrayList<HallReservation>();
        
        // Loop through each hall and get the reservations
        for (Hall hall : halls) {
            reservations.addAll(hall.getReservations());
        }
        
        return reservations;
	}

    @Override
    public HallReservation save(HallReservation hallReservation) {
        return hallReservationRepository.save(hallReservation);
    }

    @Override
    public void delete(HallReservation hallReservation) {
        hallReservationRepository.delete(hallReservation);
    }

    @Override
    public HallReservation findById(Long hallReservationId) {
        return hallReservationRepository.findOne(hallReservationId);
    }
}
