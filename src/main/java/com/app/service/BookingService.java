package com.app.service;

import com.app.model.Booking;
import com.app.model.Room;
import com.app.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository daoRepository;

    public BookingService(BookingRepository daoRepository) {
        this.daoRepository=daoRepository;
    }

    public List<Booking> findByUserId(final int id){
        return daoRepository.findByUserId(id).orElse(null);
    }

    public List<Booking> findByRoomId(final int id){
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

    public boolean checkRoomBookAble(final Date arrivalTime, final Date departureTime, final Room room){
        if (departureTime.before(arrivalTime))
            return false;

        List<Booking> currentBooking = findByRoomId(room.getId());
        if (currentBooking != null){
            for (Booking booking: currentBooking){
                if ((arrivalTime.after(booking.getArrivalTime()) || arrivalTime.equals(booking.getArrivalTime())) &&
                        (departureTime.before(booking.getDepartureTime()) || departureTime.equals(booking.getDepartureTime()))){
                    return false;
                }
            }
        }

        return true;
    }

}
