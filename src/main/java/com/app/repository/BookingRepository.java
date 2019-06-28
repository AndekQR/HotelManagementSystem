package com.app.repository;

import com.app.model.Booking;
import com.app.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Optional<List<Booking>> findByUserId(Integer id);
    Optional<List<Booking>> findByRoomId(Integer id);
    Optional<Booking> findByArrivalTimeAndDepartureTimeAndRoom(Date arrivalTime, Date departureTime, Room room);
    void deleteByRoomId(Integer id);
    void deleteByUserId(Integer id);


}
