package com.app.controller;

import com.app.helpers.BookingResult;
import com.app.helpers.Price;
import com.app.model.Booking;
import com.app.model.Room;
import com.app.service.BookingService;
import com.app.service.RoomService;
import com.app.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
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

    private static final Logger logger=LoggerFactory.getLogger(HotelController.class);

    private final RoomService roomService;
    private final BookingService bookingService;
    private final UserServiceImpl userService;

    public HotelController(RoomService roomService, BookingService bookingService, UserServiceImpl userService) {
        this.roomService=roomService;
        this.bookingService=bookingService;
        this.userService=userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd\""), true, 10));
    }

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @RequestMapping(value="/booking", method=RequestMethod.GET)
    public String booking(Model model) {
        Room wantedRoom=new Room();
        Booking booking=new Booking();
        model.addAttribute("room", wantedRoom);
        model.addAttribute("booking", booking);
        List<Booking> bookingList = bookingService.findAllBooking();
        logger.info(bookingList.toString());
        model.addAttribute("bookingList", bookingList);

        return "booking";
    }

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @RequestMapping(value="/booking", method=RequestMethod.POST)
    public String saveBooking(@ModelAttribute Room wantedRoomData, @ModelAttribute Booking booking, RedirectAttributes redirectAttributes) {

        BookingResult bookingResult=bookingService.newBooking(wantedRoomData, booking);

        if (bookingResult.result.equals("fail")) {
            redirectAttributes.addFlashAttribute("result", bookingResult.result);
            redirectAttributes.addFlashAttribute("description", bookingResult.description);
            return "redirect:/booking";
        } else if (bookingResult.result.equals("success")) {
            redirectAttributes.addFlashAttribute("result", bookingResult.result);
            redirectAttributes.addFlashAttribute("description", bookingResult.description);
            redirectAttributes.addFlashAttribute("book", bookingResult.booking);
            Room room=bookingResult.booking.getRoom();
            redirectAttributes.addFlashAttribute("room", room);
            redirectAttributes.addFlashAttribute("price", new Price());
            redirectAttributes.addFlashAttribute("total", new Price().getTotalPrice(bookingResult.booking));
            return "redirect:/pay";
        }
        else {
            bookingResult.result = "fail";
            bookingResult.description = "Unrecognized error.";
            redirectAttributes.addFlashAttribute("result", bookingResult.result);
            redirectAttributes.addFlashAttribute("description", bookingResult.description);
            return "redirect:/booking";
        }

    }
}
