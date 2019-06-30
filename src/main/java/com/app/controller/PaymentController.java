package com.app.controller;

import com.app.helpers.PaypalPaymentIntent;
import com.app.helpers.PaypalPaymentMethod;
import com.app.helpers.Price;
import com.app.helpers.URLUtils;
import com.app.model.Booking;
import com.app.service.BookingService;
import com.app.service.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final String PAYPAL_SUCCESS_URL = "pay/success";
    private final String PAYPAL_CANCEL_URL = "pay/cancel";

    private final PayPalService payPalService;
    private final BookingService bookingService;


    public PaymentController(PayPalService payPalService, BookingService bookingService) {
        this.payPalService=payPalService;
        this.bookingService=bookingService;
    }

    @RequestMapping(value="/pay", method=RequestMethod.GET)
    public String getPay(){
        return "pay";
    }

    @RequestMapping(value="/pay", method=RequestMethod.POST)
    public String pay(@ModelAttribute("book") Booking bookingOnlyId, HttpServletRequest request, RedirectAttributes redirectAttributes){
        String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
        Price price = new Price();
        Booking booking = bookingService.findById(bookingOnlyId.getId());


        Double total = price.getTotalPrice(booking);
        try {
            Payment payment = payPalService.createPayment(
                    total,
                    "PLN",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "Room number: "+booking.getRoom().getName(),
                    cancelUrl,
                    successUrl);

            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            logger.error(e.getMessage());
        }
        String result = "success";
        String description ="The payment has been made. The room is reserved.";
        redirectAttributes.addFlashAttribute("result", result);
        redirectAttributes.addFlashAttribute("description", description);
        return "redirect:/";
    }


    @RequestMapping(method = RequestMethod.GET, value = PAYPAL_CANCEL_URL)
    public String cancelPay(RedirectAttributes redirectAttributes){
        String result = "fail";
        String description ="The payment has not been made. The room is not booked.";
        redirectAttributes.addFlashAttribute("result", result);
        redirectAttributes.addFlashAttribute("description", description);

        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET, value = PAYPAL_SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, RedirectAttributes redirectAttributes){
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                String result = "success";
                String description ="The payment has been made. The room is reserved.";
                redirectAttributes.addFlashAttribute("result", result);
                redirectAttributes.addFlashAttribute("description", description);
                return "redirect:/";
            }
        } catch (PayPalRESTException e) {
            logger.error(e.getMessage());
        }
        String result = "fail";
        String description ="The payment has not been made. The room is not booked.";
        redirectAttributes.addFlashAttribute("result", result);
        redirectAttributes.addFlashAttribute("description", description);
        return "redirect:/";
    }

}
