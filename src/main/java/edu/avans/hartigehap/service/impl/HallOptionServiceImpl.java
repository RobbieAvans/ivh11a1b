package edu.avans.hartigehap.service.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.repository.HallOptionRepository;
import edu.avans.hartigehap.service.HallOptionService;

@Service("HallOptionService")
@Repository
@Transactional
public class HallOptionServiceImpl implements HallOptionService {

	@Autowired
	private HallOptionRepository hallOptionRepository;

    @PersistenceContext
    private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public List<HallOption> findAll() {
		Sort sort = new Sort(Sort.Direction.ASC, "id");
		return Lists.newArrayList(hallOptionRepository.findAll(sort));
	}

	@Override
	@Transactional(readOnly = true)
	public HallOption findById(Long hallOptionId) {
		return hallOptionRepository.findOne(hallOptionId);
	}

	@Override
	public HallOption save(HallOption hallOption) {
		return hallOptionRepository.save(hallOption);
	}

	@Override
	public boolean deleteById(long hallOptionId) {
		HallOption hallOption = hallOptionRepository.findOne(hallOptionId);

		if (hallOption != null) {
			return delete(hallOption);
		}

		return false;
	}

	@Override
	public boolean delete(HallOption hallOption) {
		if (hallOption.canBeDeleted()) {
			hallOptionRepository.delete(hallOption);

			return true;
		}

		return false;
	}

	@Override
	public List<HallOption> findByIds(List<Long> hallOptionIds) {
		return hallOptionRepository.findByIdIn(hallOptionIds);
	}

	@Override
	public boolean hasHallReservations(HallOption hallOption) {		
		Query query = em.createQuery(
				"SELECT 1 FROM HallReservation hallReservation WHERE hallReservation.hallOption.id = :hallOptionID");

		query.setParameter("hallOptionID", hallOption.getId());
		
		return !query.getResultList().isEmpty();
	}

}
