package com.app.controller.rest;

import com.app.helpers.management.BookingTable;
import com.app.helpers.management.RoomTable;
import com.app.helpers.management.UserTable;
import com.app.model.Booking;
import com.app.model.Room;
import com.app.model.User;
import com.app.service.BookingService;
import com.app.service.RoomService;
import com.app.service.UserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/management")
public class ManagementController {

    private final UserServiceImpl userService;
    private final BookingService bookingService;
    private final RoomService roomService;

    public ManagementController(UserServiceImpl userService, BookingService bookingService, RoomService roomService) {
        this.userService=userService;
        this.bookingService=bookingService;
        this.roomService=roomService;
    }

    @RequestMapping(value="/getUsers", method=RequestMethod.GET)
    public List<UserTable> getUsers(){
        List<User> allUsers = userService.findAllUsers();
       List<UserTable> userTables = allUsers.stream().map(x->{
           UserTable userTable = new UserTable();
           userTable.id = x.getId();
           userTable.email = x.getEmail();
           userTable.firstName = x.getFirstName();
           userTable.lastName = x.getLastName();
           userTable.uniqueName = x.getUniqueName();
           userTable.userType =x.getAuthorities().stream().findFirst().get().getName().name();
           return userTable;
       }).collect(Collectors.toList());
    return userTables;
    }

    @RequestMapping(value="/getBookings", method=RequestMethod.GET)
    public List<BookingTable> getBookingTableData(){
        List<Booking> bookings = bookingService.findAllBooking();
        List<BookingTable> bookingTableData = bookings.stream().map(x->{
            BookingTable bookingTableData1 = new BookingTable();
            bookingTableData1.id = x.getId();
            bookingTableData1.roomId = x.getRoom().getId();
            bookingTableData1.userId = x.getUser().getId();
            bookingTableData1.roomName = x.getRoom().getName();
            bookingTableData1.arrivalTime = x.getArrivalTime();
            bookingTableData1.departureTime = x.getDepartureTime();
            bookingTableData1.people = x.getPeople();
            return bookingTableData1;
        }).collect(Collectors.toList());
        return bookingTableData;
    }

    @RequestMapping(value="/getRooms", method=RequestMethod.GET)
    public List<RoomTable> getRooms(){
        List<Room> rooms = roomService.findAllRooms();
        List<RoomTable> roomTables = rooms.stream().map(x->{
            RoomTable roomTable = new RoomTable();
            roomTable.id = x.getId();
            roomTable.capacity = x.getCapacity();
            roomTable.internet = x.getInternet();
            roomTable.name = x.getName();
            roomTable.numberBeds = x.getNumberBeds();
            roomTable.numberOfBath = x.getNumberOfBath();
            roomTable.price = x.getPrice();
            roomTable.type = x.getType().toString();
            return roomTable;
        }).collect(Collectors.toList());
        return roomTables;
    }
}
