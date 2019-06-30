package com.app.service;

import com.app.helpers.BookingResult;
import com.app.model.Booking;
import com.app.model.Room;
import com.app.model.User;
import com.app.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository daoRepository;
    private final RoomService roomService;
    private final UserService userService;

    public BookingService(BookingRepository daoRepository, RoomService roomService, UserService userService) {
        this.daoRepository=daoRepository;
        this.roomService=roomService;
        this.userService=userService;
    }

    public Booking findById(int id){
        return daoRepository.findById(id).orElse(null);
    }

    public List<Booking> findByUserId(final int id){
        return daoRepository.findByUserId(id).orElse(null);
    }

    public List<Booking> findByRoomId(final int id){
        return daoRepository.findByRoomId(id).orElse(null);
    }

    public Booking findBooking(Date arrivalTime, Date departureTime, Room room){
        return daoRepository.findByArrivalTimeAndDepartureTimeAndRoom(arrivalTime, departureTime, room).orElse(null);
    }

    public boolean deleteByRoomId(final int id){
        daoRepository.deleteByRoomId(id);
        return findByRoomId(id) == null;
    }

    public void deleteById(int id){
        daoRepository.deleteById(id);
    }

    public boolean deleteByUserId(final int id){
        daoRepository.deleteByUserId(id);
        return findByUserId(id) == null;
    }

    public BookingResult newBooking(Room wantedRoomData, Booking booking){
        Room roomToTake;
        Booking bookingToTake=new Booking();
        BookingResult bookingResult = new BookingResult();

        List<Room> freeRooms=roomService.findAllRooms();
        if (wantedRoomData.getId() != null){
            roomToTake = wantedRoomData;
        }
        else {
            roomToTake=freeRooms.stream().filter(x ->
                    (x.getCapacity().equals(booking.getPeople())) &&
                            (x.getNumberBeds().equals(wantedRoomData.getNumberBeds())) &&
                            (x.getInternet() == wantedRoomData.getInternet()) &&
                            (x.getNumberOfBath().equals(wantedRoomData.getNumberOfBath()))
            ).findFirst().orElse(null);
        }

        if (roomToTake == null) {
            bookingResult.result = "fail";
            bookingResult.description = "No room with the given parameters.";
            return bookingResult;
        }

        if (!checkRoomBookAble(booking.getArrivalTime(), booking.getDepartureTime(), roomToTake)) {
            bookingResult.result = "fail";
            bookingResult.description = "The selected date is taken or dates are incorrect.";
            return bookingResult;
        }

        User user = userService.getActualLoggedUser();


        bookingToTake.setUser(user);
        bookingToTake.setRoom(roomService.findByName(roomToTake.getName()));
        bookingToTake.setPeople(booking.getPeople());
        bookingToTake.setArrivalTime(booking.getArrivalTime());
        bookingToTake.setDepartureTime(booking.getDepartureTime());
        saveBooking(bookingToTake);

        bookingResult.result = "success";
        bookingResult.description = "A room has been found!";
        bookingResult.booking = bookingToTake;
        return bookingResult;
    }

    public void saveBooking(final Booking booking){
        daoRepository.save(booking);
    }

    public List<Booking> findAllBooking(){
        return daoRepository.findAll();
    }

    public boolean checkRoomBookAble(final Date arrivalTime, final Date departureTime, final Room room){
        if (departureTime.before(arrivalTime) || (arrivalTime.before(new Date()))) //new Date zwraca date która jest zainicjalizowana na teraz
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
