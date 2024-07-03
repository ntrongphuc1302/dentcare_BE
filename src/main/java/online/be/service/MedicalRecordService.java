package online.be.service;

import online.be.api.MedicalRecordManagementAPI;
import online.be.entity.Account;
import online.be.entity.AppointmentPatient;
import online.be.entity.DentistServices;
import online.be.entity.MedicalRecord;
import online.be.enums.MedicalRecordEnum;
import online.be.enums.Role;
import online.be.exception.InvalidRoleException;
import online.be.exception.NotFoundException;
import online.be.model.request.MedicalRecordRequest;
import online.be.model.request.MedicalRecordUpdateRequest;
import online.be.repository.AccountRepository;
import online.be.repository.AppointmentPatientRepository;
import online.be.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MedicalRecordService {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private AppointmentPatientRepository appointmentPatientRepository;
    @Autowired
    private MedicalRecordManagementAPI medicalRecordManagementAPI;

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAllByMedicalRecordEnum(MedicalRecordEnum.ACTIVE);
    }

    public MedicalRecord getRecordById(long id) {
        return medicalRecordRepository.findById(id);
    }

    public List<MedicalRecord> getRecordsByPatientPhone(String phone) {
        return medicalRecordRepository.findByAppointmentPatient_PatientPhoneNumber(phone);
    }

    public List<MedicalRecord> getMedicalRecordsByPatientId(long id) {
        return medicalRecordRepository.findByAppointmentPatient_PatientId(id);
    }

    public MedicalRecord getAppointmentsByAppointmentPatientId(long id) {
        return medicalRecordRepository.findByAppointmentPatientId(id);
    }

    public MedicalRecord createMedicalRecord(MedicalRecordRequest medicalRecordRequest) {
        MedicalRecord medicalRecord = new MedicalRecord();
        AppointmentPatient appointmentPatient = appointmentPatientRepository.
                findById(medicalRecordRequest.getAppointmentPatientId());

        if (appointmentPatient != null) {
            medicalRecord.setAppointmentPatient(appointmentPatient);

        } else {
            throw new NotFoundException("Cannot found this Appointment");
        }
        Account account = authenticationService.getCurrentAccount();
        if (account.getRole() != Role.DENTIST) {
            throw new InvalidRoleException("The " + account.getRole() + " role is invalid");
        }
        medicalRecord.setDentist(account);
        medicalRecord.setName(medicalRecordRequest.getName());
        medicalRecord.setNote(medicalRecordRequest.getNote());
        medicalRecord.setDiagnosis(medicalRecordRequest.getDiagnosis());
        medicalRecord.setMedicalRecordEnum(MedicalRecordEnum.ACTIVE);



        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(MedicalRecordUpdateRequest medicalRecordUpdateRequest) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(medicalRecordUpdateRequest.getId());
        if (medicalRecord != null) {
            medicalRecord.setName(medicalRecordUpdateRequest.getName());

            medicalRecord.setNote(medicalRecordUpdateRequest.getNote());
            medicalRecord.setDiagnosis(medicalRecordUpdateRequest.getDiagnosis());
            return medicalRecordRepository.save(medicalRecord);
        } else {
            throw new NotFoundException("Medical Record not found!");
        }
    }

    public void deleteMedicalRecord(long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id);
        if (medicalRecord != null) {
            medicalRecord.setMedicalRecordEnum(MedicalRecordEnum.INACTIVE);
            medicalRecordRepository.save(medicalRecord);
        } else {
            throw new NotFoundException("Medical Record not found!");
        }
    }

    public List<MedicalRecord> getRecordsByDentistId(long id) {
        try {
            return medicalRecordRepository.findByDentistId(id);
        } catch (Exception e) {
            throw new NotFoundException("Dentist not found!");
        }
    }
}
