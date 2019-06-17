package com.app.service;

import com.app.helpers.RoomTypeEnum;
import com.app.model.Room;
import com.app.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository daoRoom;

    public RoomService(RoomRepository daoRoom) {
        this.daoRoom=daoRoom;
    }

    public Room findByName(String name){
       Optional<Room> room = daoRoom.findByName(name);
       return room.orElse(null);
    }


    public Room findById(int id){return daoRoom.findById(id).orElse(null);}

    public List<Room> findByPrice(int price){
        return daoRoom.findByPrice(price).orElse(null);
    }

    public List<Room> findByCapacity(int capacity){
        return daoRoom.findByCapacity(capacity).orElse(null);
    }

    public List<Room> findFreeRooms(){
        return daoRoom.findFreeRooms().orElse(null);
    }

    public List<Room> findFreeRooms(int minPrice, int maxPrice){
        return daoRoom.findFreeRooms(minPrice, maxPrice).orElse(null);
    }

    public List<Room> findFreeRooms(int minPrice, int maxPrice, RoomTypeEnum roomTypeEnum){
        return daoRoom.findFreeRooms(minPrice, maxPrice, roomTypeEnum).orElse(null);
    }

    public List<Room> findAllRooms(){
        return daoRoom.findAll();
    }

    public boolean isRoomNameUnique(String name){
        Room room = findByName(name);
        return room == null;
    }

    public  void save(Room room){
        daoRoom.save(room);
    }
}
