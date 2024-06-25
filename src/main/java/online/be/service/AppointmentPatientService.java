package online.be.service;

import online.be.entity.Account;
import online.be.entity.AppointmentPatient;
import online.be.enums.CheckInStatus;
import online.be.enums.Role;
import online.be.enums.Status;
import online.be.exception.DuplicateException;
import online.be.exception.InvalidRoleException;
import online.be.exception.NotFoundException;
import online.be.model.request.AppointmentRequest;
import online.be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentPatientService {

    @Autowired
    AppointmentPatientRepository appointmentPatientRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    SlotRepository slotRepository;

    @Autowired
    DentistServiceRepository dentistServiceRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AuthenticationService authenticationService;

    public List<AppointmentPatient> getAllAppointment() {
        return appointmentPatientRepository.findAll();
    }

    public List<AppointmentPatient> getAppointmentsByPatientId(long patientId) {
        return appointmentPatientRepository.findByPatientId(patientId);
    }

    public AppointmentPatient getAppointmentById(long id) {
        return appointmentPatientRepository.findById(id);
    }

    public List<AppointmentPatient> getAppointmentsDentistId(long id) {
        Account account = accountRepository.findById(id);
        if (account.getRole() == Role.DENTIST) {
            return appointmentPatientRepository.findByDentistServices_AccountId(id);
        } else {
            throw new InvalidRoleException("The " + account.getRole() + " is invalid");
        }
    }

    public AppointmentPatient createAppointment(AppointmentRequest appointmentRequest) {
        AppointmentPatient appointmentPatient = appointmentPatientRepository.
                findBySlotIdAndPatientIdAndDate(appointmentRequest.getSlotId(), appointmentRequest.getPatientId(),
                        appointmentRequest.getDate());
        Account account = authenticationService.getCurrentAccount();
        if (appointmentPatient == null) {
            AppointmentPatient appointment = new AppointmentPatient();
            var patient = patientRepository.findById(appointmentRequest.getPatientId());
            appointment.setPatient(patient);
            var slot = slotRepository.findById(appointmentRequest.getSlotId());
            appointment.setSlot(slot);
            var dentistService = dentistServiceRepository.findById(appointmentRequest.getDentistServiceId());
            appointment.setDentistServices(dentistService);
            appointment.setDate(appointmentRequest.getDate());
            appointment.setStatus(CheckInStatus.PROCESSING);
            appointment.setAccount2(account);
            return appointmentPatientRepository.save(appointment);
        } else {
            throw new DuplicateException("These id have been existed");
        }
    }

    public AppointmentPatient updateAppointment(AppointmentRequest appointmentRequest) {
//        AppointmentPatient appointmentPatient = appointmentPatientRepository.
//                findBySlotIdAndPatientId(appointmentRequest.getSlotId(), appointmentRequest.getPatientId());
        AppointmentPatient appointmentPatient = appointmentPatientRepository.findById(appointmentRequest.getId());
        if (appointmentPatient != null) {

            var patient = patientRepository.findById(appointmentRequest.getPatientId());
            appointmentPatient.setPatient(patient);
            var slot = slotRepository.findById(appointmentRequest.getSlotId());
            appointmentPatient.setSlot(slot);
            var dentistService = dentistServiceRepository.findById(appointmentRequest.getDentistServiceId());
            appointmentPatient.setDentistServices(dentistService);
//            appointmentPatient.setStatus(Status.ACTIVE);

            return appointmentPatientRepository.save(appointmentPatient);
        } else {
            throw new NotFoundException("These id have not been existed");
        }
    }

    public void deleteAppointment(AppointmentRequest appointmentRequest) {
        AppointmentPatient appointmentPatient = appointmentPatientRepository.
                findById(appointmentRequest.getId());

        if (appointmentPatient != null) {

            appointmentPatient.setStatus(CheckInStatus.CANCEL);

            appointmentPatientRepository.save(appointmentPatient);
        } else {
            throw new NotFoundException("These id have not been existed");
        }
    }
}
