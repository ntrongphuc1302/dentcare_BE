package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.PatientRequest;
import online.be.model.request.PatientUpdateRequest;
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

    @GetMapping("/name/{name}")
    public ResponseEntity getPatientByName(@PathVariable String name) {
        return ResponseEntity.ok(patientService.getPatientByName(name));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity getPatientByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(patientService.getPatientByPhone(phone));
    }

    @PostMapping
    public ResponseEntity createPatient(@RequestBody PatientRequest patientRequest) {
        return ResponseEntity.ok(patientService.createPatient(patientRequest));
    }

    @PutMapping
    public ResponseEntity updatePatient(@RequestBody PatientUpdateRequest patientUpdateRequest) {
        return ResponseEntity.ok(patientService.updatePatient(patientUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient deleted successfully");
    }

}
