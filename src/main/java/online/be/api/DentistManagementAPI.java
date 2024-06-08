package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.service.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dentist")
@SecurityRequirement(name = "api")
public class DentistManagementAPI {

    @Autowired
    DentistService dentistService;

    @GetMapping
    public ResponseEntity getAllClinics() {
        return ResponseEntity.ok(dentistService.getAllDentist());
    }

    @GetMapping("/{id}")
    public ResponseEntity getClinicById(@PathVariable long id) {
        return ResponseEntity.ok(dentistService.getDentistById(id));
    }
}
