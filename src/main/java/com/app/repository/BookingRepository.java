package com.app.repository;

import com.app.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Optional<List<Booking>> findByUserId(Integer id);
    Optional<List<Booking>> findByRoomId(Integer id);
    void deleteByRoomId(Integer id);
    void deleteByUserId(Integer id);


}
