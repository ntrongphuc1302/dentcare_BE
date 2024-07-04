package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.entity.Account;
import online.be.entity.WorkingDayOff;
import online.be.model.request.DayOffRequest;
import online.be.service.WorkingDayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/working-day-off")
@SecurityRequirement(name = "api")
public class WorkingDayOffAPI {

    @Autowired
    WorkingDayOffService workingDayOffService;
    @PostMapping
    public ResponseEntity createWorkingDayOff(@RequestBody DayOffRequest dayOffRequest) {
        return ResponseEntity.ok(workingDayOffService.createWorkingDayOff(dayOffRequest));
    }

    @GetMapping
    public ResponseEntity getAllWorkingDayOffs() {
        return ResponseEntity.ok(workingDayOffService.getAllWorkingDayOffs());
    }

    @PutMapping
    public ResponseEntity updateWorkingDayOff(@PathVariable long id, @RequestBody DayOffRequest dayOffRequest) {
        return ResponseEntity.ok(workingDayOffService.updateWorkingDayOff(id, dayOffRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity getWorkingDayOffById(@PathVariable long id) {
        return ResponseEntity.ok(workingDayOffService.getWorkingDayOffById(id));
    }

    @GetMapping("/dentist/{id}")
    public ResponseEntity getWorkingDayOffByDentistId(@PathVariable long id) {
        return ResponseEntity.ok(workingDayOffService.getWorkingDayOffByDentistId(id));
    }

    @GetMapping("/slot/{id}")
    public ResponseEntity getWorkingDayOffBySlotId(@PathVariable long id) {
        return ResponseEntity.ok(workingDayOffService.getWorkingDayOffBySlotId(id));
    }
}
