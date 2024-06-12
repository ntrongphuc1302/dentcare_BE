package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.SlotRequest;
import online.be.model.request.SlotUpdateRequest;
import online.be.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity createSlot(@RequestBody SlotRequest slotRequest) {
        return ResponseEntity.ok(slotService.createSlot(slotRequest));
    }

    @PutMapping
    public ResponseEntity updateSlot(@RequestBody SlotUpdateRequest slotUpdateRequest) {
        return ResponseEntity.ok(slotService.updateSlot(slotUpdateRequest));
    }
}