package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.MedicalRecordRequest;
import online.be.model.request.MedicalRecordUpdateRequest;
import online.be.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicalrecord")
@SecurityRequirement(name = "api")
public class MedicalRecordManagementAPI {

    @Autowired
    MedicalRecordService medicalRecordService;

    @GetMapping
    public ResponseEntity getAllMedicalRecords() {
        return ResponseEntity.ok(medicalRecordService.getAllMedicalRecords());
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
}