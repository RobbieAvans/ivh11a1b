package edu.avans.hartigehap.domain.agenda;

import java.util.Date;

import edu.avans.hartigehap.domain.HallReservation;

public class ReservationAgendaItemAdapter implements AgendaItem {

	private HallReservation reservation;
	
	/**
	 * Receive a HallReservation and translate it to an AgendaItem
	 * 
	 * @param reservation
	 */
	public ReservationAgendaItemAdapter(HallReservation reservation)
	{
		this.reservation = reservation;
	}
	
	@Override
	public Date getStartDate() {
		// TODO
		return new Date();
	}

	@Override
	public Date getEndDate() {
		// TODO
		return new Date();
	}

	@Override
	public String getDescription() {
		return reservation.getDescription();
	}
}
