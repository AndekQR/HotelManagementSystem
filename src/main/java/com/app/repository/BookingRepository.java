package com.app.repository;

import com.app.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Optional<Booking> findByUserId(Integer id);
    Optional<Booking> findByRoomId(Integer id);
    void deleteByRoomId(Integer id);
    void deleteByUserId(Integer id);


}
