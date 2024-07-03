package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.entity.Room;
import online.be.model.request.RoomRequest;
import online.be.model.request.RoomUpdateRequest;
import online.be.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/room")
@SecurityRequirement(name = "api")
public class DentalRoomManagementAPI {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("search-by-name/{name}")
    public ResponseEntity getRoomByName(@PathVariable String name) {
        return ResponseEntity.ok(roomService.getRoomByName(name));
    }

    @GetMapping("/dentist/{id}")
    public ResponseEntity getRoomsByDentistId(@PathVariable long id) {
        return ResponseEntity.ok(roomService.getRoomsByDentistId(id));
    }

    @GetMapping("/clinic/{id}")
    public ResponseEntity getRoomsByClinicId(@PathVariable long id) {
        return ResponseEntity.ok(roomService.getRoomsByClinicId(id));
    }

    @PatchMapping("/active/room/{id}")
    public ResponseEntity activeRoom(@PathVariable long id) {
        return ResponseEntity.ok(roomService.activeRoom(id));
    }

    @PostMapping
    public ResponseEntity createRoom(@RequestBody RoomRequest roomRequest) {
        return ResponseEntity.ok(roomService.createRoom(roomRequest));
    }

    @PutMapping
    public ResponseEntity updateRoom(@RequestBody RoomUpdateRequest roomUpdateRequest) {
        return ResponseEntity.ok(roomService.updateRoom(roomUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRoom(@PathVariable long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok("Room deleted successfully");
    }

}
