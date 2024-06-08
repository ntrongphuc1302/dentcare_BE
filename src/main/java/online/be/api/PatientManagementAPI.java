package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.PatientRequest;
import online.be.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/patient")
@SecurityRequirement(name = "api")

public class PatientManagementAPI {

    @Autowired
    PatientService patientService;

    @GetMapping
    public ResponseEntity getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PostMapping
    public ResponseEntity createPatient(@RequestBody PatientRequest patientRequest) {
        return ResponseEntity.ok(patientService.createPatient(patientRequest));
    }

    @PutMapping
    public ResponseEntity updatePatient(@RequestBody PatientRequest patientRequest) {
        return ResponseEntity.ok(patientService.updatePatient(patientRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient deleted successfully");
    }

}
