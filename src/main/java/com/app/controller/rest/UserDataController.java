package com.app.controller.rest;

import com.app.helpers.BookingTableData;
import com.app.model.Booking;
import com.app.model.User;
import com.app.service.BookingService;
import com.app.service.UserServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data")
public class UserDataController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final BookingService bookingService;

    public UserDataController(UserServiceImpl userService, PasswordEncoder passwordEncoder, BookingService bookingService) {
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
        this.bookingService=bookingService;
    }

    @RequestMapping(value="/changeUserData", method=RequestMethod.POST, produces="application/json")
    public Map<String, String> changeUserData(@RequestBody User user){
        User loggedUser = userService.getActualLoggedUser();
        if ((user.getFirstName() != null) && (user.getLastName() != null)){
            loggedUser.setFirstName(user.getFirstName());
            loggedUser.setLastName(user.getLastName());
            userService.update(loggedUser);
        }
        else if (user.getUniqueName() != null){
            loggedUser.setUniqueName(user.getUniqueName());
            userService.update(loggedUser);
        }
        else if (user.getEmail() != null){
            loggedUser.setEmail(user.getEmail());
            userService.update(loggedUser);
        }
        else if (user.getPassword() != null){
            if (loggedUser.getPassword() != null){
                loggedUser.setPassword(passwordEncoder.encode(user.getPassword()));
                userService.update(loggedUser);
            }
        }


        return Collections.singletonMap("response", "Created");
    }

    @RequestMapping(value="/getBookingTableData", method=RequestMethod.GET)
    public List<BookingTableData> getBookingTableData(){
        List<Booking> bookings = bookingService.findByUserId(userService.getActualLoggedUser().getId());
        List<BookingTableData> bookingTableData = bookings.stream().map(x->{
            BookingTableData bookingTableData1 = new BookingTableData();
            bookingTableData1.roomName = x.getRoom().getName();
            bookingTableData1.arrivalTime = x.getArrivalTime();
            bookingTableData1.departureTime = x.getDepartureTime();
            bookingTableData1.people = x.getPeople();
            return bookingTableData1;
        }).collect(Collectors.toList());
        return bookingTableData;
    }
}
