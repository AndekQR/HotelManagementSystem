package com.app.repository;

import com.app.helpers.RoomTypeEnum;
import com.app.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByName(String name);
    Optional<List<Room>> findByPrice(Integer price);
    Optional<List<Room>> findByCapacity(Integer capacity);

    @Query(value="SELECT m.* from Room AS m JOIN (SELECT DISTINCT d.* FROM Room AS d LEFT OUTER JOIN Booking AS c ON c.room = d.id WHERE (c.room IS NULL) ORDER BY d.name) AS t ON m.id = t.id ", nativeQuery=true)
    Optional<List<Room>> findFreeRooms();

    @Query(value="SELECT m.* from Room AS m JOIN (SELECT DISTINCT d.* FROM Room AS d LEFT OUTER JOIN Booking AS c ON c.room = d.id WHERE (c.room IS NULL) ORDER BY d.name) AS t ON m.id = t.id WHERE m.price >= :minimum and m.price <= :maximum", nativeQuery = true)
    Optional<List<Room>> findFreeRooms(@Param("minimum") Integer minPrice, @Param("maximum") Integer maxPrice);

    @Query(value="SELECT m.* from Room AS m JOIN (SELECT DISTINCT d.* FROM Room AS d LEFT OUTER JOIN Booking AS c ON c.room = d.id WHERE (c.room IS NULL) ORDER BY d.name) AS t ON m.id = t.id WHERE  m.price >= :minimum and m.price <= :maximum and m.type = :type", nativeQuery = true)
    Optional<List<Room>> findFreeRooms(@Param("minimum") Integer minPrice, @Param("maximum") Integer maxPrice, @Param("type") RoomTypeEnum type);

}
