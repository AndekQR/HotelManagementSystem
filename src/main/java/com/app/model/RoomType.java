package com.app.model;

import com.app.helpers.RoomTypeEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class RoomType {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Column(name="TYPE", nullable=false, unique=true)
    @Enumerated(EnumType.STRING)
    private RoomTypeEnum type;

    @Column(name="PRICE", nullable=false)
    private Integer basePrice;

    @OneToMany(mappedBy="type")
    private Set<Room> rooms = new HashSet<>();

    public RoomType(RoomTypeEnum roomTypeEnum, int basePrice){
        this.type =roomTypeEnum;
        this.basePrice=basePrice;
    }

    public RoomType(){}

}
