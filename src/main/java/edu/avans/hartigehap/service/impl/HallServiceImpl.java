package edu.avans.hartigehap.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.repository.HallRepository;
import edu.avans.hartigehap.service.HallService;

@Service("hallService")
@Repository
@Transactional(rollbackFor = StateException.class)
public class HallServiceImpl implements HallService {

    @Autowired
    private HallRepository hallRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Hall> findAll() {
        return (List<Hall>) hallRepository.findAll();
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
    public boolean deleteById(long hallId) {
        Hall hall = hallRepository.findOne(hallId);

        if (hall != null) {
            return delete(hall);
        }

        return false;
    }

    @Override
    public boolean delete(Hall hall) {
        if (hall.canBeDeleted()) {
            hallRepository.delete(hall);

            return true;
        }

        return false;
    }

    /**
     * 
     * @param hall
     * @param partOfDays - should be a sorted ascending list with at least one partOfDay
     * @return
     */
    @Override
    public boolean isAvailableFor(Hall hall, List<PartOfDay> partOfDays) {
        Query query = em.createQuery(
                "SELECT count(*) FROM Hall hall JOIN hall.reservations reservation JOIN reservation.partOfDays partOfDay WHERE hall.id = :hallID AND (partOfDay.startTime BETWEEN :startDate AND :endDate OR partOfDay.endTime BETWEEN :startDate AND :endDate OR (partOfDay.startTime <= :startDate AND partOfDay.endTime >= :endDate))");

        // Add 1 second to start so same end dates won't be treated as between
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(partOfDays.get(0).getStartTime());
        startDate.add(Calendar.SECOND, 1);
        
        // Subtract 1 second form end so same start dates won't be treated as between
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(partOfDays.get(partOfDays.size() - 1).getEndTime());
        endDate.add(Calendar.SECOND, -1);
        
        query.setParameter("hallID", hall.getId());
        query.setParameter("startDate", startDate.getTime());   
        query.setParameter("endDate", endDate.getTime());
        
        return (long) query.getSingleResult() == 0;
    }

}
