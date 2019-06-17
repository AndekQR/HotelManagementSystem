package com.app.service;

import com.app.model.Booking;
import com.app.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository daoRepository;

    public BookingService(BookingRepository daoRepository) {
        this.daoRepository=daoRepository;
    }

    public Booking findByUserId(final int id){
        return daoRepository.findByUserId(id).orElse(null);
    }

    public Booking findByRoomId(final int id){
        return daoRepository.findByRoomId(id).orElse(null);
    }

    public boolean deleteByRoomId(final int id){
        daoRepository.deleteByRoomId(id);
        return findByRoomId(id) == null;
    }

    public boolean deleteByUserId(final int id){
        daoRepository.deleteByUserId(id);
        return findByUserId(id) == null;
    }

    public void saveBooking(final Booking booking){
        daoRepository.save(booking);
    }

    public List<Booking> findAllBooking(){
        return daoRepository.findAll();
    }

}
