package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.MedicalRecordRequest;
import online.be.model.request.MedicalRecordUpdateRequest;
import online.be.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical-record")
@SecurityRequirement(name = "api")
public class MedicalRecordManagementAPI {

    @Autowired
    MedicalRecordService medicalRecordService;

    @GetMapping
    public ResponseEntity getAllMedicalRecords() {
        return ResponseEntity.ok(medicalRecordService.getAllMedicalRecords());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getRecordById(@PathVariable long id) {
        return ResponseEntity.ok(medicalRecordService.getRecordById(id));
    }

    @GetMapping("/patient/phone/{phone}")
    public ResponseEntity getRecordsByPatientPhone(@PathVariable String phone) {
        return ResponseEntity.ok(medicalRecordService.getRecordsByPatientPhone(phone));
    }

    @GetMapping("/patient/id/{id}")
    public ResponseEntity getRecordsByPatientId(@PathVariable long id) {
        return ResponseEntity.ok(medicalRecordService.getMedicalRecordsByPatientId(id));
    }

    @GetMapping("/appointment/{id}")
    public ResponseEntity getAppointmentsByAppointmentPatientId(@PathVariable long id) {
        return ResponseEntity.ok(medicalRecordService.getAppointmentsByAppointmentPatientId(id));
    }

    @PostMapping
    public ResponseEntity createMedicalRecord(@RequestBody MedicalRecordRequest medicalRecordRequest) {
        return ResponseEntity.ok(medicalRecordService.createMedicalRecord(medicalRecordRequest));
    }

    @PutMapping
    public ResponseEntity updateMedicalRecord(@RequestBody MedicalRecordUpdateRequest medicalRecordUpdateRequest){
        return ResponseEntity.ok(medicalRecordService.updateMedicalRecord(medicalRecordUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMedicalRecord(@PathVariable long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.ok("Medical Record deleted successfully");
    }

    @GetMapping("/dentist/{id}")
    public ResponseEntity getRecordsByDentistId(@PathVariable long id) {
        return ResponseEntity.ok(medicalRecordService.getRecordsByDentistId(id));
    }
}