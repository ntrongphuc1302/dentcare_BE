package online.be.api;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.enums.CheckInStatus;
import online.be.model.request.AppointmentRequest;
import online.be.service.AppointmentPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @GetMapping("/patient/id/{patientId}")
    public ResponseEntity<?> getAppointmentsByPatientId(@PathVariable long patientId) {
        return ResponseEntity.ok(appointmentPatientService.getAppointmentsByPatientId(patientId));
    }

    @GetMapping("/dentist/{id}")
    public ResponseEntity getAppointmentsDentistId(@PathVariable long id) {
        return ResponseEntity.ok(appointmentPatientService.getAppointmentsDentistId(id));
    }

    @GetMapping("/patient/phone/{phone}")
    public ResponseEntity<?> getAppointmentsByPatientPhone(@PathVariable String phone) {
        return ResponseEntity.ok(appointmentPatientService.getAppointmentsByPatientPhone(phone));
    }

    @GetMapping("/patient/{pId}/dentist/{dId}/date/{date}")
    public ResponseEntity getAppointmentsByPatientIDAndDentistIdAndDate
            (@PathVariable long pId, @PathVariable long dId, @PathVariable LocalDate date) {

        return ResponseEntity.ok
                (appointmentPatientService.getAppointmentsByPatientIDAndDentistIdAndDate(pId, dId, date));
    }

    @GetMapping("/date/between/{startDate}/{endDate}")
//    @GetMapping("/date/between")
    public ResponseEntity getAppointmentsByDateBetween(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return ResponseEntity.ok(appointmentPatientService.getAppointmentsByDateBetween(startDate, endDate));
    }

    @GetMapping("/date/between/{startDate}/{endDate}/dentist/{id}")
//    @GetMapping("/date/between")
    public ResponseEntity getAppointmentByDateBetweenAndDentistId
            (@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable long id) {
        return ResponseEntity.ok(appointmentPatientService.
                getAppointmentByDateBetweenAndDentistId(startDate, endDate, id));
    }

    @GetMapping("/date/between/{startDate}/{endDate}/clinic/{id}")
//    @GetMapping("/date/between")
    public ResponseEntity getAppointmentByDateBetweenAndClinicId
            (@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable long id) {
        return ResponseEntity.ok(appointmentPatientService.
                getAppointmentByDateBetweenAndClinicId(startDate, endDate, id));
    }

    @PostMapping
    public ResponseEntity createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        return ResponseEntity.ok(appointmentPatientService.createAppointment(appointmentRequest));
    }

    @PutMapping
    public ResponseEntity updateAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        return ResponseEntity.ok(appointmentPatientService.updateAppointment(appointmentRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAppointment(@PathVariable long id) {
        appointmentPatientService.deleteAppointment(id);
        return ResponseEntity.ok("Delete successfully");
    }

    @GetMapping("/patient/{id}/date/{date}")
    public ResponseEntity getAppointmentsByPatientIdAndDate(@PathVariable long id, @PathVariable LocalDate date) {
        return ResponseEntity.ok(appointmentPatientService.getAppointmentsByPatientIdAndDate(id, date));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity getAppointmentsByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(appointmentPatientService.getAppointmentsByDate(date));
    }

    @GetMapping("/staff/{id}")
    public ResponseEntity getAppointmentsByStaffId(@PathVariable long id) {
        return ResponseEntity.ok(appointmentPatientService.getAppointmentsByStaffId(id));
    }

    @GetMapping("/clinic/{id}")
    public ResponseEntity getAppointmentByDentalClinicId(@PathVariable long id) {
        return ResponseEntity.ok((appointmentPatientService.getAppointmentByDentalClinicId(id)));
    }

    @PatchMapping("/id/{id}/status/{status}")
    public ResponseEntity checkInByStaff(@PathVariable long id, @PathVariable CheckInStatus status) {
        return ResponseEntity.ok(appointmentPatientService.checkInByStaff(id, status));
    }

    @GetMapping("/clinic/{id}/date/{date}")
    public ResponseEntity getAppointmentByDentalClinicIdAndDate(@PathVariable long id,
                                                                @PathVariable LocalDate date) {
        return ResponseEntity.ok(appointmentPatientService.getAppointmentByDentalClinicIdAndDate(id, date));
    }
}
