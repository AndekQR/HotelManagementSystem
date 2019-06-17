package com.app.service;

import com.app.helpers.RoomTypeEnum;
import com.app.model.RoomType;
import com.app.repository.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeService {
    private final RoomTypeRepository daoRoomType;

    public RoomTypeService(RoomTypeRepository daoRoomType) {
        this.daoRoomType=daoRoomType;
    }

    public RoomType findByType(RoomTypeEnum type){
        return daoRoomType.findByType(type).orElse(null);
    }

    public List<RoomType> findAll(){
        return daoRoomType.findAll();
    }

    public void save(RoomType roomType){
        daoRoomType.save(roomType);
    }
}
