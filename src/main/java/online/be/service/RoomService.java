package online.be.service;

import online.be.entity.DentalClinic;
import online.be.entity.Room;
import online.be.enums.RoomEnum;
import online.be.exception.NotFoundException;
import online.be.model.request.RoomRequest;
import online.be.model.request.RoomUpdateRequest;
import online.be.repository.ClinicRepository;
import online.be.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    public Room getRoomById(long id) {
        return roomRepository.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAllByRoomEnum(RoomEnum.ACTIVE);
    }

    public Room getRoomByName(String name) {
        return roomRepository.findRoomByName(name);
    }

    public Room createRoom(RoomRequest roomRequest) {
        Room newRoom = new Room();
        newRoom.setName(roomRequest.getName());
        newRoom.setRoomEnum(RoomEnum.ACTIVE);
        var clinic = clinicRepository.findById(roomRequest.getClinicId()).
                orElseThrow(() -> new NotFoundException("Clinic not found with id "));;
        newRoom.setClinic(clinic);

        return roomRepository.save(newRoom);
    }

    public Room updateRoom(RoomUpdateRequest roomUpdateRequest) {
        Room existingRoom = roomRepository.findById(roomUpdateRequest.getId());
        if (existingRoom != null) {
            existingRoom.setName(roomUpdateRequest.getName());
            return roomRepository.save(existingRoom);
        } else {
            throw new NotFoundException("Room not found with id " + roomUpdateRequest.getId());
        }
    }

    public void deleteRoom(long id) {
        Room existingRoom = roomRepository.findById(id);
        if (existingRoom != null) {
            existingRoom.setRoomEnum(RoomEnum.INACTIVE);
            roomRepository.save(existingRoom);
        } else {
            throw new NotFoundException("Room not found with id " + id);
        }
    }

}