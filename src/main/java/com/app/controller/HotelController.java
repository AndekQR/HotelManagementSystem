package com.app.controller;

import com.app.model.Booking;
import com.app.model.Room;
import com.app.service.BookingService;
import com.app.service.RoomService;
import com.app.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class HotelController {

    private static final Logger logger = LoggerFactory.getLogger(HotelController.class);

    private final RoomService roomService;
    private final BookingService bookingService;
    private final UserServiceImpl userService;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd\""), true, 10));
    }

    public HotelController(RoomService roomService, BookingService bookingService, UserServiceImpl userService) {
        this.roomService=roomService;
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @RequestMapping(value="/booking", method=RequestMethod.GET)
    public String booking(Model model) {
        Room wantedRoom = new Room();
        Booking booking = new Booking();
        model.addAttribute("room", wantedRoom);
        model.addAttribute("booking", booking);

        return "booking";
    }

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @RequestMapping(value="/booking", method=RequestMethod.POST)
    public String saveBooking(@ModelAttribute Room wantedRoomData, @ModelAttribute Booking booking){
        Room roomToTake;
        Booking bookingToTake = new Booking();

        List<Room> freeRooms = roomService.findFreeRooms();
        roomToTake = freeRooms.stream().filter(x ->
                (x.getCapacity() >= booking.getPeople()) &&
                        (x.getNumberBeds() >= wantedRoomData.getNumberBeds()) &&
                        (x.getInternet() == wantedRoomData.getInternet()) &&
                        (x.getNumberOfBath() >=wantedRoomData.getNumberOfBath())
        ).findFirst().get();

        logger.info("pokoj: "+roomToTake.getName());

        User loggedUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.app.model.User user = userService.findByEmail(loggedUser.getUsername());

        logger.info("ilosc osob: "+ booking.getPeople());
        logger.info("data arrvial time: "+booking.getArrivalTime());
        logger.info("wynajety: "+ roomService.findByName(roomToTake.getName()).getName());

        bookingToTake.setUser(user);
        bookingToTake.setRoom(roomService.findByName(roomToTake.getName()));
        bookingToTake.setPeople(booking.getPeople());
        bookingToTake.setArrivalTime(booking.getArrivalTime());
        bookingToTake.setDepartureTime(booking.getDepartureTime());


        bookingService.saveBooking(bookingToTake);
        return "login";
    }
}
