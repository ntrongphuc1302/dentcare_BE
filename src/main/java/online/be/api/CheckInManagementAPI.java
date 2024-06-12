package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.CheckInRequest;
import online.be.model.request.CheckInUpdateRequest;
import online.be.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/checkin")
@SecurityRequirement(name = "api")
public class CheckInManagementAPI {

    @Autowired
    CheckInService checkInService;

    @GetMapping
    public ResponseEntity getAllCheckIns() {
        return ResponseEntity.ok(checkInService.getAllCheckIns());
    }

    @PostMapping
    public ResponseEntity createCheckIn(@RequestBody CheckInRequest checkInRequest) {
        return ResponseEntity.ok(checkInService.createCheckIn(checkInRequest));
    }

    @PutMapping
    public ResponseEntity updateCheckIn(@RequestBody CheckInUpdateRequest checkInUpdateRequest) {
        return ResponseEntity.ok(checkInService.updateCheckIn(checkInUpdateRequest));
    }

}
