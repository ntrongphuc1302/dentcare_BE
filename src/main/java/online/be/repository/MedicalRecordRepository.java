package online.be.repository;

import online.be.entity.MedicalRecord;
import online.be.enums.MedicalRecordEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    MedicalRecord findById(long id);
    List<MedicalRecord> findAllByMedicalRecordEnum(MedicalRecordEnum medicalRecordEnum);
}
