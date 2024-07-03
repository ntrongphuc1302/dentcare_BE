package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.entity.Slot;
import online.be.model.request.SlotRequest;
import online.be.model.request.SlotUpdateRequest;
import online.be.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/slot")
@SecurityRequirement(name = "api")
public class SlotManagementAPI {

    @Autowired
    SlotService slotService;

    @GetMapping
    public ResponseEntity getAllSlots() {
        return ResponseEntity.ok(slotService.getAllSlots());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity getSlotByName(@PathVariable String name) {
        return ResponseEntity.ok(slotService.getSlotByName(name));
    }

    @GetMapping("/available/dentist/{dentistId}/day-off/{dayOff}")
    public ResponseEntity getAvailableSlots(@PathVariable long dentistId, @PathVariable LocalDate dayOff) {
        return ResponseEntity.ok(slotService.getAvailableSlots(dentistId, dayOff));
    }

//    @GetMapping("/dentists/{id}")
//    public ResponseEntity getSlotsByDentist(@PathVariable long id) {
//        return ResponseEntity.ok(slotService.getSlotsByDentist(id));
//    }
//
//    @GetMapping("/room/{id}")
//    public ResponseEntity getSlotsByRoom(@PathVariable long id) {
//        return ResponseEntity.ok(slotService.getSlotsByRoom(id));
//    }

//    @GetMapping("/date/{date}")
//    public ResponseEntity getSlotsByDate(@PathVariable String date) {
//        return ResponseEntity.ok(slotService.getSlotsByDate(date));
//    }

    @PostMapping
    public ResponseEntity createSlot(@RequestBody SlotRequest slotRequest) {
        return ResponseEntity.ok(slotService.createSlot(slotRequest));
    }

    @PutMapping
    public ResponseEntity updateSlot(@RequestBody SlotUpdateRequest slotUpdateRequest) {
        return ResponseEntity.ok(slotService.updateSlot(slotUpdateRequest));
    }
}
