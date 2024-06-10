package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.QualificationRequest;
import online.be.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/qualification")
@SecurityRequirement(name = "api")
public class QualificationManagementAPI {

    @Autowired
    QualificationService qualificationService;

    @GetMapping
    public ResponseEntity getAllQualification() {
        return ResponseEntity.ok(qualificationService.getAllQualification());
    }

    @GetMapping("/by-manager")
    public ResponseEntity getAllQualificationByManager() {
        return ResponseEntity.ok(qualificationService.getAllQualificationByManager());
    }

    @GetMapping("/{id}")
    public ResponseEntity getQualificationById(@PathVariable long id) {
        return ResponseEntity.ok(qualificationService.getQualificationById(id));
    }

    @PostMapping
    public ResponseEntity createQualification(@RequestBody QualificationRequest qualificationRequest) {
        return ResponseEntity.ok(qualificationService.createQualification(qualificationRequest));
    }

    @PutMapping
    public ResponseEntity updateQualification(@RequestBody QualificationRequest qualificationRequest) {
        return ResponseEntity.ok(qualificationService.updateQualification(qualificationRequest));
    }

    @DeleteMapping
    public ResponseEntity deleteQualification(@RequestBody QualificationRequest qualificationRequest) {
        qualificationService.deleteQualification(qualificationRequest);
        return ResponseEntity.ok("Delete qualification successfully");
    }
}
