package online.be.repository;

import online.be.entity.MedicalRecord;
import online.be.enums.MedicalRecordEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    MedicalRecord findById(long id);
    List<MedicalRecord> findAllByMedicalRecordEnum(MedicalRecordEnum medicalRecordEnum);
    List<MedicalRecord> findByAppointmentPatient_PatientId(long id);
    List<MedicalRecord> findByAppointmentPatient_PatientPhoneNumber(String phone);
    MedicalRecord findByAppointmentPatientId(long id);
    List<MedicalRecord> findByDentistId(long id);

}
