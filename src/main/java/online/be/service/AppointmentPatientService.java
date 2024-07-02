package online.be.service;

import online.be.entity.Account;
import online.be.entity.AppointmentPatient;
import online.be.entity.Patient;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public List<AppointmentPatient> getAppointmentsByPatientIDAndDentistIdAndDate(long patientId, long denId, String date) {
        Account account = accountRepository.findById(denId);
        Patient patient = patientRepository.findById(patientId);
        if (account.getRole() != Role.DENTIST) {
            throw new InvalidRoleException("The " + account.getRole() + " is invalid");
        }
        if(patient == null) {
            throw new NotFoundException("Cannot found this patient");
        }
        return appointmentPatientRepository.
                findByPatientIdAndDentistServices_AccountIdAndDate(patientId, denId, date);
    }

    public List<AppointmentPatient> getAppointmentsDentistId(long id) {
        Account account = accountRepository.findById(id);
        if (account.getRole() == Role.DENTIST) {
            return appointmentPatientRepository.findByDentistServices_AccountId(id);
        } else {
            throw new InvalidRoleException("The " + account.getRole() + " is invalid");
        }
    }

    public List<AppointmentPatient> getAppointmentsByPatientPhone(String phone) {
        return appointmentPatientRepository.findByPatient_PhoneNumber(phone);
    }

    public AppointmentPatient createAppointment(AppointmentRequest appointmentRequest) {
        AppointmentPatient appointmentPatient = appointmentPatientRepository.
                findBySlotIdAndPatientIdAndDate(appointmentRequest.getSlotId(),
                        appointmentRequest.getPatientId(),
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

    private boolean checkAppointmentAvailable(long id){
        boolean result = false;
        LocalDate currentDate = LocalDate.now();
        AppointmentPatient appointmentPatient = appointmentPatientRepository.findById(id);
        if (appointmentPatient != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate appointmentDate = LocalDate.parse(appointmentPatient.getDate(), formatter);

            if (!appointmentDate.isBefore(currentDate)) {
                result = true;
            }
        }

        return result;
    }


    public AppointmentPatient updateAppointment(AppointmentRequest appointmentRequest) {
//        AppointmentPatient appointmentPatient = appointmentPatientRepository.
//                findBySlotIdAndPatientId(appointmentRequest.getSlotId(), appointmentRequest.getPatientId());
//       boolean check = checkAppointmentAvailable(appointmentRequest.getId());
//        if (check == false) {
//            throw new RuntimeException("Stupid");
//        }
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

    public void deleteAppointment(long id) {
        AppointmentPatient appointmentPatient = appointmentPatientRepository.
                findById(id);

        if (appointmentPatient != null) {

            appointmentPatient.setStatus(CheckInStatus.CANCEL);

            appointmentPatientRepository.save(appointmentPatient);
        } else {
            throw new NotFoundException("These id have not been existed");
        }
    }

    public List<AppointmentPatient> getAppointmentsByPatientIdAndDate(long id, String date) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new RuntimeException("Date is invalid");
        }
        if (patientRepository.findById(id) == null){
            throw new NotFoundException("Patient not found");
        }
        return appointmentPatientRepository.findByPatientIdAndDate(id, date);
    }

    public List<AppointmentPatient> getAppointmentsByDate(String date) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new RuntimeException("Date is invalid");
        }
        return appointmentPatientRepository.findByDate(date);
    }

    public List<AppointmentPatient> getAppointmentsByStaffId(long id) {
        Account account = accountRepository.findById(id);
        if (account.getRole() == Role.STAFF) {
            return appointmentPatientRepository.findByAccountId(id);
        } else {
            throw new InvalidRoleException("The " + account.getRole() + " is invalid");
        }
    }

    public AppointmentPatient checkInByStaff(long id, CheckInStatus status) {
        AppointmentPatient appointmentPatient = appointmentPatientRepository.findById(id);
        Account staff = authenticationService.getCurrentAccount();
        if (appointmentPatient == null) {
            throw new NotFoundException("These id have not been existed");
        }
        appointmentPatient.setStatus(status);
        appointmentPatient.setAccount(staff);

        return appointmentPatientRepository.save(appointmentPatient);

    }
}
