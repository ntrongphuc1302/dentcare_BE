package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.WorkingHoursRequest;
import online.be.model.request.WorkingHoursUpdateRequest;
import online.be.service.WorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/workinghours")
@SecurityRequirement(name = "api")
public class WorkingHoursManagementAPI {

    @Autowired
    WorkingHoursService workingHoursService;

    @GetMapping
    public ResponseEntity getAllWorkingHours() {
        return ResponseEntity.ok(workingHoursService.getAllWorkingHours());
    }

    @PostMapping
    public ResponseEntity createWorkingHours(@RequestBody WorkingHoursRequest workingHoursRequest) {
        return ResponseEntity.ok(workingHoursService.createWorkingHours(workingHoursRequest));
    }

    @PutMapping
    public ResponseEntity updateWorkingHours(@RequestBody WorkingHoursUpdateRequest workingHoursUpdateRequest) {
        return ResponseEntity.ok(workingHoursService.updateWorkingHours(workingHoursUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteWorkingHours(@PathVariable long id) {
        workingHoursService.deleteWorkingHours(id);
        return ResponseEntity.ok("Working Hours deleted successfully");
    }
}
