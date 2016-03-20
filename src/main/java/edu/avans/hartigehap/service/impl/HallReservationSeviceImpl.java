package edu.avans.hartigehap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservationOption;
import edu.avans.hartigehap.repository.HallReservationRepository;
import edu.avans.hartigehap.service.HallReservationService;
import edu.avans.hartigehap.service.HallService;
import edu.avans.hartigehap.web.controller.rs.body.HallReservationRequest;

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
    public HallReservation findById(long id) {
        return hallReservationRepository.findOne(id);
    }

    @Override
    public void delete(HallReservation hallReservation) {
        hallReservationRepository.delete(hallReservation);
    }

    @Override
    public HallReservation update(HallReservation hallReservationPointer,
            HallReservationRequest hallReservationRequest) throws Exception {
        // Get the hall where we will save it on
        Hall hall = hallReservationRequest.getHall();

        if (hallReservationPointer.getHall().equals(hall)) {
            // The same hall, remove it for now we will add it later
            hall.removeReservation(hallReservationPointer);
        } else {
            // This is weird... But it is necessary that the reservations are all loaded
            hall.getReservations().size();
        }
        
        // Reset hallReservationPointer
        hallReservationPointer.reset();
        
        List<HallOption> hallOptions = hallReservationRequest.getHallOptions();
        List<HallOption> removeHallOptions = new ArrayList<>();

        for (HallOption currentHallOption : hallReservationPointer.getHallOptions()) {
            if (!hallOptions.contains(currentHallOption)) {
                removeHallOptions.add(currentHallOption);
            } else {
                // Remove from hallOptions so hallOptions will be a list
                // with HallOptions that should be added to the reservation.
                hallOptions.remove(currentHallOption);
            }
        }

        // Add addHallOptions
        for (HallOption hallOption : hallOptions) {
            hallReservationPointer = new HallReservationOption(hallReservationPointer, hallOption);
        }

        if (removeHallOptions.size() > 0) {
            // Remove hallOptions
            List<Long> hallReservationOptionsToRemove = new ArrayList<>();
            
            // Loop through all reservation options
            HallReservation hallReservationCursor = hallReservationPointer;
            HallReservationOption previous = null;
            while (hallReservationCursor instanceof HallReservationOption) {
                HallReservationOption hallReservationOption = (HallReservationOption) hallReservationCursor;
                HallOption hallOption = hallReservationOption.getHallOption();
                // Check if the option should be deleted
                if (removeHallOptions.contains(hallOption)) {
                    if (previous != null && previous.getHallReservation().equals(hallReservationOption)) {
                        // Change the pointer of the previous option
                        previous.setHallReservation(hallReservationOption.getHallReservation());
                    }

                    if (hallReservationOption.equals(hallReservationPointer)) {
                        // Set the pointer of the last reservation option to the
                        // next option
                        hallReservationPointer = hallReservationOption.getHallReservation();
                    }
                    
                    hallReservationCursor = hallReservationOption.getHallReservation();
                    // Set to null -> otherwise the whole reservation will be
                    // deleted because of the cascade.all option
                    hallReservationOption.setHallReservation(null);
                    hallReservationOptionsToRemove.add(hallReservationOption.getId());
                } else {
                    // No delete, keep this option
                    hallReservationCursor = hallReservationOption.getHallReservation();
                    previous = hallReservationOption;
                }
            }
            
            hallReservationRepository.deleteByIdIn(hallReservationOptionsToRemove);
        }

        // Set other
        hallReservationPointer.setDescription(hallReservationRequest.getDescription());
        hallReservationPointer.setCustomer(hallReservationRequest.getCustomer());
        hallReservationPointer.setPartOfDays(hallReservationRequest.getPartOfDays());
        hallReservationPointer.setState(hallReservationRequest.getState());
        
        // Add to hall
        hall.addReservation(hallReservationPointer);
        
        // Save it
        hallService.save(hall);

        return hallReservationPointer;
    }
}
