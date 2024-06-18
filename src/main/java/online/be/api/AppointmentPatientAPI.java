package online.be.api;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.model.request.AppointmentRequest;
import online.be.service.AppointmentPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/appointment-patient")
@SecurityRequirement(name = "api")
public class AppointmentPatientAPI {

    @Autowired
    AppointmentPatientService appointmentPatientService;

    @GetMapping
    public ResponseEntity getAllAppointment() {
        return ResponseEntity.ok(appointmentPatientService.getAllAppointment());
    }

    @GetMapping("/{id}")
    public ResponseEntity getAppointmentById(@PathVariable long id) {
        return ResponseEntity.ok(appointmentPatientService.getAppointmentById(id));
    }

    @PostMapping
    public ResponseEntity createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        return ResponseEntity.ok(appointmentPatientService.createAppointment(appointmentRequest));
    }

    @PutMapping
    public ResponseEntity updateAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        return ResponseEntity.ok(appointmentPatientService.updateAppointment(appointmentRequest));
    }

    @DeleteMapping
    public ResponseEntity deleteAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        appointmentPatientService.deleteAppointment(appointmentRequest);
        return ResponseEntity.ok("Delete successfully");
    }
}
