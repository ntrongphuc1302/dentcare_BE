package online.be.repository;

import online.be.entity.Patient;
import online.be.enums.PatientEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findById(long id);
    List<Patient> findAllByPatientEnum(PatientEnum patientEnum);
    Patient findByName(String name);
    Patient findByPhoneNumber(String phone);
    List<Patient> findByAccountId(long id);
    List<Patient> findByAccountIdAndPatientEnum(long id, PatientEnum patientEnum);
    Patient findByNameAndAgeAndGenderAndAddress(String name, String age, Boolean gender, String address);
}
