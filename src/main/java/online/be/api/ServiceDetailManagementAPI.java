package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.ServiceDetailRequest;
import online.be.model.request.ServiceDetailUpdateRequest;
import online.be.service.ServiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/service")
@SecurityRequirement(name = "api")

public class ServiceDetailManagementAPI {

    @Autowired
    ServiceDetailService serviceDetailService;

    @GetMapping
    public ResponseEntity getAllServiceDetail() {
        return ResponseEntity.ok(serviceDetailService.getAllServiceDetail());
    }

    @GetMapping("/{id}")
    public ResponseEntity getServiceById(@PathVariable long id) {
        return ResponseEntity.ok(serviceDetailService.getServiceById(id));
    }

    @GetMapping("/name/{name}")
    public  ResponseEntity getServiceByKeyName(@PathVariable String name) {
        return ResponseEntity.ok(serviceDetailService.getServiceByKeyName(name));
    }

    @PostMapping
    public ResponseEntity createDentistService(@RequestBody ServiceDetailRequest serviceDetailRequest) {
        return ResponseEntity.ok(serviceDetailService.createService(serviceDetailRequest));
    }

    @PutMapping
    public ResponseEntity updateDentistService
            (@RequestBody ServiceDetailUpdateRequest serviceDetailUpdateRequest) {
        return ResponseEntity.ok(serviceDetailService.updateDentistService(serviceDetailUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDentistService(@PathVariable long id) {
        serviceDetailService.deleteDentistService(id);
        return ResponseEntity.ok("Dentist Service deleted successfully");
    }
}
