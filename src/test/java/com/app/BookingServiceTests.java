package com.app;

import com.app.helpers.RoomTypeEnum;
import com.app.model.Booking;
import com.app.model.Room;
import com.app.model.User;
import com.app.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingServiceTests {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomTypeService roomTypeService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityService authorityService;

    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    Date arrivalTime = null, departureTime = null;

    @Before
    public void checkIfInitialize(){
        if (userService.findByEmail("johndoe_199x@mail.com") == null){
            User user = userService.newUser();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setUniqueName("johndoe");
            user.setEmail("johndoe_199x@mail.com");
            user.setPassword("j");
            userService.save(user);
        }
        if (roomTypeService.findByType(RoomTypeEnum.NORMAL) == null){
            com.app.model.RoomType roomTypeNORMAL = new com.app.model.RoomType(RoomTypeEnum.NORMAL, 200);

            roomTypeService.save(roomTypeNORMAL);

        }
        if (roomService.findByName("1") == null){
            roomService.save(new Room("1", 200, 2, 1, true, 1, roomTypeService.findByType(RoomTypeEnum.NORMAL)));
        }
        try {
            arrivalTime = ft.parse("2020-05-20");
            departureTime = ft.parse("2020-06-20");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (bookingService.findBooking(arrivalTime, departureTime, roomService.findByName("1")) == null){
            Booking booking = new Booking();
            booking.setDepartureTime(departureTime);
            booking.setArrivalTime(arrivalTime);
            booking.setPeople(2);
            booking.setRoom(roomService.findByName("1"));
            booking.setUser(userService.findByEmail("johndoe_199x@mail.com"));
            bookingService.saveBooking(booking);
        }
    }

    @Test
    public void shouldReturnBooking(){
        Booking booking = bookingService.findBooking(arrivalTime, departureTime, roomService.findByName("1"));
        assertThat(booking).isNotNull();
    }


}
