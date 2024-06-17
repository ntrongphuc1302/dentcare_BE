package online.be.repository;

import online.be.entity.Room;
import online.be.enums.RoomEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findById(long id);
    List<Room> findAllByRoomEnum(RoomEnum roomEnum);
    Room findRoomByName(String name);
}
