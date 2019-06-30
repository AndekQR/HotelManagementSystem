package com.app.controller.rest;

import com.app.controller.PaymentController;
import com.app.helpers.BookingResult;
import com.app.helpers.dp.Event;
import com.app.helpers.dp.EventCreateParams;
import com.app.helpers.dp.RowsData;
import com.app.model.Booking;
import com.app.model.Room;
import com.app.service.BookingService;
import com.app.service.RoomService;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.std.DateSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/dp")
public class DayPilotController {

    private static final Logger logger = LoggerFactory.getLogger(DayPilotController.class);
    private final BookingService bookingService;
    private final RoomService roomService;
    private final PaymentController paymentController;

    public DayPilotController(BookingService bookingService, RoomService roomService, PaymentController paymentController) {
        this.bookingService=bookingService;
        this.roomService=roomService;
        this.paymentController=paymentController;
    }

    @RequestMapping(value="/getRowsData")
    public Iterable<RowsData> getRowsData(){
        List<Room> allRooms = roomService.findAllRooms();
        List<RowsData> rowsData = allRooms.stream().map(x->{
            RowsData rowsData1 = new RowsData();
            rowsData1.name = "Room nr. "+ x.getName();
            rowsData1.id = x.getId();
            return rowsData1;
                })
                .distinct()
                .collect(Collectors.toList());

        return rowsData;
    }

    @RequestMapping(value="/getEvents")
    public Iterable<Event> getEvents(){
        List<Booking> bookings = bookingService.findAllBooking();
        List<Event> events = bookings.stream().map(book ->{
            Event event = new Event();
            event.id = book.getId();
            event.text = "Reservation";
            event.start = book.getArrivalTime();
            event.end = book.getDepartureTime();
            event.resource = book.getRoom().getId();
            return event;
        }).collect(Collectors.toList());

        return events;
    }

    @RequestMapping(value="/createReservation", method=RequestMethod.POST)
    @JsonSerialize(using=DateSerializer.class)
    public Event createReservation(@RequestBody EventCreateParams params){
        Room room = roomService.findById(params.resource);
        Booking booking = new Booking();
        booking.setArrivalTime(params.start);
        booking.setDepartureTime(params.end);
        booking.setPeople(params.people);
        BookingResult bookingResult = bookingService.newBooking(room, booking);

        if (bookingResult.result.equals("success")){
            Event event = new Event();
            event.id = bookingResult.booking.getId();
            event.text = "Reservation";
            event.start = bookingResult.booking.getArrivalTime();
            event.end = bookingResult.booking.getDepartureTime();
            event.resource = bookingResult.booking.getRoom().getId();
            return event;
        }
        else {
            logger.info(bookingResult.description);
            return null;
        }

    }

    @RequestMapping(value="/toPay", method=RequestMethod.POST)
    public void toPay(HttpServletRequest request, RedirectAttributes redirectAttributes, @RequestBody Event event){
        Booking booking = bookingService.findById(event.id);
        paymentController.pay(booking, request, redirectAttributes);

    }

}
