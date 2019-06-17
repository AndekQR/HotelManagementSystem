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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String saveBooking(@ModelAttribute Room wantedRoomData, @ModelAttribute Booking booking, RedirectAttributes redirectAttributes){
        Room roomToTake;
        Booking bookingToTake = new Booking();
        String result = "fail";
        String description;

        List<Room> freeRooms = roomService.findAllRooms();
        roomToTake = freeRooms.stream().filter(x ->
                (x.getCapacity().equals(booking.getPeople())) &&
                (x.getNumberBeds().equals(wantedRoomData.getNumberBeds())) &&
                (x.getInternet() == wantedRoomData.getInternet()) &&
                (x.getNumberOfBath().equals(wantedRoomData.getNumberOfBath()))
        ).findFirst().orElse(null);

        if (roomToTake == null){
            description = "No room with the given parameters.";
            redirectAttributes.addFlashAttribute("result", result);
            redirectAttributes.addFlashAttribute("description", description);
            return "redirect:/booking";
        }

        if (!bookingService.checkRoomBookAble(booking.getArrivalTime(), booking.getDepartureTime(), roomToTake)){
            description = "The selected date is taken.";
            redirectAttributes.addFlashAttribute("result", result);
            redirectAttributes.addFlashAttribute("description", description);
            return "redirect:/booking";
        }

        User loggedUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.app.model.User user = userService.findByEmail(loggedUser.getUsername());

        bookingToTake.setUser(user);
        bookingToTake.setRoom(roomService.findByName(roomToTake.getName()));
        bookingToTake.setPeople(booking.getPeople());
        bookingToTake.setArrivalTime(booking.getArrivalTime());
        bookingToTake.setDepartureTime(booking.getDepartureTime());
        bookingService.saveBooking(bookingToTake);

        result = "success";
        description = "The room has been booked.";
        redirectAttributes.addFlashAttribute("result", result);
        redirectAttributes.addFlashAttribute("description", description);
        return "redirect:/booking";
    }
}
