package com.app.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="USER", nullable=false)
    private User user;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ROOM", nullable=false)
    private Room room;

    @Column(name="PEOPLE", nullable=false)
    private Integer people;

    @Column(name="ARRIVAL_TIME", nullable=false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date arrivalTime;

    @Column(name="DEPARTURE_TIME", nullable=false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date departureTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking=(Booking) o;

        if (!id.equals(booking.id)) return false;
        if (!user.equals(booking.user)) return false;
        if (!room.equals(booking.room)) return false;
        if (!people.equals(booking.people)) return false;
        if (!arrivalTime.equals(booking.arrivalTime)) return false;
        return departureTime.equals(booking.departureTime);
    }

    @Override
    public int hashCode() {
        int result= ((id == null)?0:id.hashCode()); //delete? id
        result=31 * result + ((user == null)?0:user.hashCode());
        result=31 * result + ((room == null)?0:room.hashCode());
        result=31 * result + ((people == null)?0:people.hashCode());
        result=31 * result + ((arrivalTime == null)?0:arrivalTime.hashCode());
        result=31 * result + ((departureTime == null)?0:departureTime.hashCode());
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room=room;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people=people;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime=arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime=departureTime;
    }

    public Booking(){}
}
