package com.app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    //1, 2, 3, 4....
    @Column(name="NAME", nullable=false, unique=true)
    private String name;

    @Column(name="PRICE", nullable=false)
    private Integer price; //baseprice(RoomTypeEnum) + price(bo rózna ilosc łożek itd) = calokwita cena za pokoj

    @Column(name="CAPACITY", nullable=false)
    private Integer capacity;

    @Column(name="NUMBEROFBEDS", nullable=false)
    private Integer numberBeds;

    @Column(name="INTERNET", nullable=false)
    private Boolean internet;

    @Column(name="NUMEROFBATHS", nullable=false)
    private Integer numberOfBath;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="TYPE", nullable=false)
    private RoomType type;

    public Room(String name, Integer price, Integer capacity, Integer numberBeds, Boolean internet, Integer numberOfBath, RoomType type) {
        this.name=name;
        this.price=price;
        this.capacity=capacity;
        this.numberBeds=numberBeds;
        this.internet=internet;
        this.numberOfBath=numberOfBath;
        this.type=type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room=(Room) o;

        if (!id.equals(room.id)) return false;
        if (!name.equals(room.name)) return false;
        if (!price.equals(room.price)) return false;
        if (!capacity.equals(room.capacity)) return false;
        if (!numberOfBath.equals(room.numberOfBath)) return false;
        if (!numberBeds.equals(room.numberBeds)) return false;
        if (!internet.equals(room.internet)) return false;
        return type == room.type;
    }

    @Override
    public int hashCode() {
        int result=id.hashCode();
        result=31 * result + name.hashCode();
        result=31 * result + price.hashCode();
        result=31 * result + capacity.hashCode();
        result=31 * result + numberOfBath.hashCode();
        result=31 * result + numberBeds.hashCode();
        result=31 * result + internet.hashCode();
        result=31 * result + type.hashCode();
        return result;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price=price;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity=capacity;
    }

    public Integer getNumberOfBath() {
        return numberOfBath;
    }

    public void setNumberOfBath(Integer numberOfBath) {
        this.numberOfBath=numberOfBath;
    }

    public Integer getNumberBeds() {
        return numberBeds;
    }

    public void setNumberBeds(Integer numberBeds) {
        this.numberBeds=numberBeds;
    }

    public Boolean getInternet() {
        return internet;
    }

    public void setInternet(Boolean internet) {
        this.internet=internet;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(com.app.model.RoomType type) {
        this.type=type;
    }



    public Room() {
    }
}
