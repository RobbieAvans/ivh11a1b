package edu.avans.hartigehap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallReservation;
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
    public HallReservation findById(long id) {
        return hallReservationRepository.findOne(id);
    }

    @Override
    public void delete(HallReservation hallReservation) {
        hallReservationRepository.delete(hallReservation);
    }

    /**
     * @Override public HallReservation update(HallReservationAPIWrapper
     *           newReservation, HallReservation oldReservation) { List
     *           <HallOption> oldHallOptions = oldReservation.getHallOptions();
     *           List<HallOption> newHallOptions =
     *           newReservation.getHallOptions(); // // for (HallOption
     *           newHallOption : newHallOptions) { // if
     *           (oldHallOptions.contains(newHallOption)) { // // Remove from
     *           both arrays // oldHallOptions.remove(newHallOption); //
     *           newHallOptions.remove(newHallOption); // } // } // // // Remove
     *           oldHallOptions that are left // for (HallOption oldHallOption :
     *           oldHallOptions) { // // } // // // Decorate with newHallOptions
     * 
     *           if (oldHallOptions.size() > 1) { HallReservationOption
     *           oldReservationComplete = (HallReservationOption)
     *           oldReservation;
     * 
     * 
     *           }
     * 
     *           // Get the hall where we will save it on Hall hall =
     *           newReservation.getHall();
     * 
     *           List<HallOption> hallOptions = newReservation.getHallOptions();
     *           Iterator<HallOption> hallOptionsIterator =
     *           hallOptions.iterator();
     * 
     *           if (hallOptionsIterator.hasNext()) { // Create the
     *           HallReservation HallReservation reservation = new
     *           ConcreteHallReservation(hallOptionsIterator.next());
     * 
     *           while (hallOptionsIterator.hasNext()) { reservation = new
     *           HallReservationOption(reservation, hallOptionsIterator.next());
     *           }
     * 
     *           hall.addReservation(reservation); hallService.save(hall);
     * 
     *           return reservation; }
     * 
     *           return null; }
     */
}
