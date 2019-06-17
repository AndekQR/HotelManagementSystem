package com.app.repository;

import com.app.helpers.RoomTypeEnum;
import com.app.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
    Optional<RoomType> findByType(RoomTypeEnum type);
}
