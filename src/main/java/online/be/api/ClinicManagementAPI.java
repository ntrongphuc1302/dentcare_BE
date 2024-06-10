package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.entity.DentalClinic;
import online.be.model.request.ClinicByManagerRequest;
import online.be.model.request.ClinicRequest;
import online.be.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/clinic")
@SecurityRequirement(name = "api")
public class ClinicManagementAPI {

    @Autowired
    ClinicService clinicService;

    @GetMapping
    public ResponseEntity getAllClinics() {
        return ResponseEntity.ok(clinicService.getAllClinics());
    }

    @GetMapping("/{id}")
    public ResponseEntity getClinicById(@PathVariable long id) {
        return ResponseEntity.ok(clinicService.getClinicById(id));
    }

    @PostMapping
    public ResponseEntity createClinic(@RequestBody ClinicRequest clinicRequest) {
        return ResponseEntity.ok(clinicService.createClinic(clinicRequest));
    }

    @PutMapping
    public ResponseEntity updateClinic(@RequestBody ClinicByManagerRequest clinicByManagerRequest) {
        return ResponseEntity.ok(clinicService.updateClinic( clinicByManagerRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClinic(@PathVariable long id) {
        clinicService.deleteClinic(id);
        return ResponseEntity.ok("Clinic deleted successfully");
    }


}
