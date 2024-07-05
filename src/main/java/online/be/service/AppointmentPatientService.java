package online.be.service;

import online.be.entity.Account;
import online.be.entity.AppointmentPatient;
import online.be.entity.DentalClinic;
import online.be.entity.Patient;
import online.be.enums.CheckInStatus;
import online.be.enums.Role;
import online.be.enums.Status;
import online.be.exception.BadRequestException;
import online.be.exception.DuplicateException;
import online.be.exception.InvalidRoleException;
import online.be.exception.NotFoundException;
import online.be.model.EmailDetail;
import online.be.model.request.AppointmentRequest;
import online.be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
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

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    TokenService tokenService;
    @Autowired
    EmailService emailService;

    public List<AppointmentPatient> getAllAppointment() {
        List<AppointmentPatient> appointmentPatients = appointmentPatientRepository.findAll();
        appointmentPatients.sort(Comparator.comparing(AppointmentPatient::getDate, Comparator.reverseOrder()));
        return appointmentPatients;
    }

    public List<AppointmentPatient> getAppointmentsByPatientId(long patientId) {
        List<AppointmentPatient> appointmentPatients = appointmentPatientRepository.findByPatientId(patientId);
        appointmentPatients.sort(Comparator.comparing(AppointmentPatient::getDate, Comparator.reverseOrder()));
        return appointmentPatients;
    }

    public AppointmentPatient getAppointmentById(long id) {
        return appointmentPatientRepository.findById(id);
    }

    public List<AppointmentPatient> getAppointmentsByPatientIDAndDentistIdAndDate
            (long patientId, long denId, LocalDate date) {
        Account account = accountRepository.findById(denId);
        Patient patient = patientRepository.findById(patientId);
        if (account.getRole() != Role.DENTIST) {
            throw new InvalidRoleException("The " + account.getRole() + " is invalid");
        }
        if(patient == null) {
            throw new NotFoundException("Cannot found this patient");
        }
        List<AppointmentPatient> appointmentPatients= appointmentPatientRepository.
                findByPatientIdAndDentistServices_AccountIdAndDate(patientId, denId, date);
        appointmentPatients.sort(Comparator.comparing(AppointmentPatient::getDate, Comparator.reverseOrder()));
        return appointmentPatients;
    }

    public List<AppointmentPatient> getAppointmentsDentistId(long id) {
        Account account = accountRepository.findById(id);
        if (account.getRole() == Role.DENTIST) {
            List<AppointmentPatient> appointmentPatients= appointmentPatientRepository.findByDentistServices_AccountId(id);
            appointmentPatients.sort(Comparator.comparing(AppointmentPatient::getDate, Comparator.reverseOrder()));
            return appointmentPatients;
        } else {
            throw new InvalidRoleException("The " + account.getRole() + " is invalid");
        }
    }

    public List<AppointmentPatient> getAppointmentsByPatientPhone(String phone) {
        List<AppointmentPatient> appointmentPatients = appointmentPatientRepository.findByPatient_PhoneNumber(phone);
        appointmentPatients.sort(Comparator.comparing(AppointmentPatient::getDate, Comparator.reverseOrder()));
        return appointmentPatients;
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

            sendEmailAfterCompleted(account);
            return appointmentPatientRepository.save(appointment);
        } else {
            throw new DuplicateException("These id have been existed");
        }
    }

    // thong bao cho nguoi dat lich ngay sau khi hoan thanh dat lich
    public void sendEmailAfterCompleted(Account account) {
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(account.getEmail());
//        emailDetail.setFullName(account.getFullName());
        emailDetail.setSubject("Successful Appointment for account " + account.getEmail() + "!");
        emailDetail.setMsgBody("Chuc mung ban sap di kham rang!");
        emailDetail.setButtonValue("View Web");
        emailDetail.setLink("http://dentcare.website");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMailTemplate(emailDetail);
            }
        };
        new Thread(r).start();
    }

    //thong bao truoc 1 ngay
    @Scheduled(cron = "0 0 0 * * ?") //dem nguoc luc 12h dem moi ngay
    public void checkAndNotifyAppointment(){
        List<AppointmentPatient> accList = appointmentPatientRepository.findByStatus(CheckInStatus.PROCESSING);
        for(AppointmentPatient customer : accList){
            if(customer.getDate() != null && customer.getDate().isBefore(LocalDate.now())){
                Account cus = customer.getAccount2();
                sendEmailBeforeAppointmentDateOneDay(cus);
            }
        }
    }

    public void sendEmailBeforeAppointmentDateOneDay(Account account) {
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(account.getEmail());
//        emailDetail.setFullName(account.getFullName());
        emailDetail.setSubject("Tomorrow appointment for account " + account.getEmail() + "!");
        emailDetail.setMsgBody("Chuc mung ban sap di kham rang!");
        emailDetail.setButtonValue("CHECK NOW");
        emailDetail.setLink("http://dentcare.website");
//        emailService.sendMailTemplate(emailDetail);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMailTemplate(emailDetail);
            }
        };
        new Thread(r).start();
    }

//    private boolean checkAppointmentAvailable(long id){
//        boolean result = false;
//        LocalDate currentDate = LocalDate.now();
//        AppointmentPatient appointmentPatient = appointmentPatientRepository.findById(id);
//        if (appointmentPatient != null) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate appointmentDate = LocalDate.parse(appointmentPatient.getDate(), formatter);
//
//            if (!appointmentDate.isBefore(currentDate)) {
//                result = true;
//            }
//        }
//
//        return result;
//    }


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
        AppointmentPatient appointmentPatient = appointmentPatientRepository.findById(id);

        if (appointmentPatient != null) {

            appointmentPatient.setStatus(CheckInStatus.CANCEL);

            appointmentPatientRepository.save(appointmentPatient);
        } else {
            throw new NotFoundException("These id have not been existed");
        }
    }

    public List<AppointmentPatient> getAppointmentsByPatientIdAndDate(long id, LocalDate date) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new RuntimeException("Date is invalid");
        }
        if (patientRepository.findById(id) == null){
            throw new NotFoundException("Patient not found");
        }
        List<AppointmentPatient> appointmentPatients = appointmentPatientRepository.findByPatientIdAndDate(id, date);
        appointmentPatients.sort(Comparator.comparing(AppointmentPatient::getDate, Comparator.reverseOrder()));
        return appointmentPatients;
    }

    public List<AppointmentPatient> getAppointmentsByDate(LocalDate date) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new RuntimeException("Date is invalid");
        }
        List<AppointmentPatient> appointmentPatients = appointmentPatientRepository.findByDate(date);
        appointmentPatients.sort(Comparator.comparing(AppointmentPatient::getDate, Comparator.reverseOrder()));
        return appointmentPatients;
    }

    public List<AppointmentPatient> getAppointmentsByStaffId(long id) {
        Account account = accountRepository.findById(id);
        if (account.getRole() == Role.STAFF) {
            List<AppointmentPatient> appointmentPatients = appointmentPatientRepository.findByAccountId(id);
            appointmentPatients.sort(Comparator.comparing(AppointmentPatient::getDate, Comparator.reverseOrder()));
            return appointmentPatients;
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

    public List<AppointmentPatient> getAppointmentsByDateBetween(LocalDate startDate, LocalDate endDate) {
        List<AppointmentPatient> appointmentPatients= appointmentPatientRepository.findByDateBetween(startDate, endDate);
        appointmentPatients.sort(Comparator.comparing(AppointmentPatient::getDate, Comparator.reverseOrder()));
        return appointmentPatients;
    }

    public List<AppointmentPatient> getAppointmentByDateBetweenAndDentistId
            (LocalDate startDate, LocalDate endDate, long id) {
        List<AppointmentPatient> appointmentPatients= appointmentPatientRepository.findByDateBetweenAndDentistServices_AccountId
                (startDate, endDate, id);
        appointmentPatients.sort(Comparator.comparing(AppointmentPatient::getDate, Comparator.reverseOrder()));
        return appointmentPatients;
    }

    public List<AppointmentPatient> getAppointmentByDateBetweenAndClinicId
            (LocalDate startDate, LocalDate endDate, long id) {
        List<AppointmentPatient> appointmentPatients = appointmentPatientRepository.findByDateBetweenAndDentistServices_Account_DentalClinicId
                (startDate, endDate, id);
        appointmentPatients.sort(Comparator.comparing(AppointmentPatient::getDate, Comparator.reverseOrder()));
        return appointmentPatients;
    }

    public List<AppointmentPatient> getAppointmentByDentalClinicId(long id) {
        DentalClinic clinic = clinicRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Clinic not found with id " + id));;

        return appointmentPatientRepository.findByDentistServices_Account_DentalClinicId(id);
    }

    public List<AppointmentPatient> getAppointmentByDentalClinicIdAndDate(long id, LocalDate date) {
        DentalClinic clinic = clinicRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Clinic not found with id " + id));;
        return appointmentPatientRepository.
                    findByDentistServices_Account_DentalClinicIdAndDate(id, date);

    }
}
