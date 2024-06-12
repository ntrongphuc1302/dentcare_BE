package online.be.service;

import online.be.entity.AppointmentPatient;
import online.be.entity.MedicalRecord;
import online.be.enums.MedicalRecordEnum;
import online.be.exception.NotFoundException;
import online.be.model.request.MedicalRecordRequest;
import online.be.model.request.MedicalRecordUpdateRequest;
import online.be.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAllByMedicalRecordEnum(MedicalRecordEnum.ACTIVE);
    }

    public MedicalRecord createMedicalRecord(MedicalRecordRequest medicalRecordRequest) {
        MedicalRecord medicalRecord = new MedicalRecord();
        AppointmentPatient appointmentPatient = new AppointmentPatient();
        medicalRecord.setNote(medicalRecordRequest.getNote());
        medicalRecord.setDiagnosis(medicalRecordRequest.getDiagnosis());
        medicalRecord.setMedicalRecordEnum(MedicalRecordEnum.ACTIVE);
        medicalRecord.setAppointmentPatient(appointmentPatient);
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(MedicalRecordUpdateRequest medicalRecordUpdateRequest) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(medicalRecordUpdateRequest.getId());
        if (medicalRecord != null) {
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
}
