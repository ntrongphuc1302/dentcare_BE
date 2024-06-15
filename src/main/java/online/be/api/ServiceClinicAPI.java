package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.ServiceClinicRequest;
import online.be.service.ServiceClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/service-clinic")
@SecurityRequirement(name = "api")
public class ServiceClinicAPI {

    @Autowired
    ServiceClinicService serviceClinicService;

    @GetMapping("/search-service-by-clinic-id/{id}")
    public ResponseEntity getAllServiceDetailsByDentalClinicId(@PathVariable long id) {
        return ResponseEntity.ok(serviceClinicService.
                getAllServiceDetailsByDentalClinicId(id));
    }

    @GetMapping("/search-clinic-by-service-id/{id}")
    public ResponseEntity getAllDentalClinicsByServiceDetailId(@PathVariable long id) {
        return ResponseEntity.ok(serviceClinicService.
                getAllDentalClinicsByServiceDetailId(id));
    }

    @PostMapping
    public ResponseEntity enrollServiceInClinic(@RequestBody ServiceClinicRequest serviceClinicRequest) {
        serviceClinicService.enrollServiceInClinic(serviceClinicRequest);
        return ResponseEntity.ok("Enroll successfull");
    }
}
