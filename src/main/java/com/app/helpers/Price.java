package com.app.helpers;

/*Cena za pokój jest w modelu*/

import com.app.model.Booking;

public final class Price {
    private Double bed = 12.5;
    private Double internet = 10.0;
    private Double batch = 15.0;
    private Double normal = 50.0;
    private Double apartment = 100.0;
    private Double exclusive = 200.0;

    public Double getTypePrice(String type){
        if (type.equals("normal"))
            return normal;
        else if (type.equals("apartment"))
            return apartment;
        else if (type.equals("exclusive"))
            return exclusive;
        return null;
    }

    public Double getTotalPrice(Booking booking){
        String roomType = booking.getRoom().getType().toString().toLowerCase();
        Double total = booking.getRoom().getPrice() +
                getTypePrice(roomType) +
                booking.getRoom().getNumberBeds()*getBed() +
                booking.getRoom().getNumberOfBath()*getBatch() +
                ((booking.getRoom().getInternet())?0:getInternet());

        return total;
    }

    public Double getBed() {
        return bed;
    }

    public Double getInternet() {
        return internet;
    }

    public Double getBatch() {
        return batch;
    }

    public Double getNormal() {
        return normal;
    }

    public Double getApartment() {
        return apartment;
    }

    public Double getExclusive() {
        return exclusive;
    }
}
