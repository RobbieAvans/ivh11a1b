package edu.avans.hartigehap.domain;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public interface Observer {
    void notify(HallReservation hallReservation);
}