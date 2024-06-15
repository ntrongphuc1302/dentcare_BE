package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.DentistServiceRequest;
import online.be.service.DentistServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/dentist-service")
@SecurityRequirement(name = "api")
public class DentistServiceAPI {

    @Autowired
    DentistServiceService dentistServiceService;

    @GetMapping
    public ResponseEntity getAllDentistService() {
        return ResponseEntity.ok(dentistServiceService.getAllDentistService());
    }

    @GetMapping("/{id}")
    public ResponseEntity getDentistServiceById(@PathVariable long id) {
        return ResponseEntity.ok(dentistServiceService.getDentistServiceById(id));
    }

    @GetMapping("/search-by-service-id/{id}")
    public ResponseEntity getAllDentistByServiceId(@PathVariable long id) {
        return ResponseEntity.ok(dentistServiceService.getAllDentistByServiceId(id));
    }

    @GetMapping("/search-by-dentist-id/{id}")
    public ResponseEntity getAllServiceByDentistId(@PathVariable long id) {
        return ResponseEntity.ok(dentistServiceService.getAllServiceByDentistId(id));
    }

    @PostMapping
    public ResponseEntity createDentistService(@RequestBody DentistServiceRequest dentistServiceRequest) {
        return ResponseEntity.ok(dentistServiceService.createDentistService(dentistServiceRequest));
    }

    @PutMapping
    public ResponseEntity updateDentistService(@RequestBody DentistServiceRequest dentistServiceRequest) {
        return ResponseEntity.ok(dentistServiceService.updateDentistService(dentistServiceRequest));
    }

    @DeleteMapping
    public ResponseEntity deleteDentistService(@RequestBody DentistServiceRequest dentistServiceRequest) {
        dentistServiceService.deleteDentistService(dentistServiceRequest);
        return ResponseEntity.ok("Delete successfully");
    }
}
