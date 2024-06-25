package online.be.repository;

import online.be.entity.AppointmentPatient;
import online.be.entity.Patient;
import online.be.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentPatientRepository extends JpaRepository<AppointmentPatient, Long> {
    AppointmentPatient findById(long id);
    AppointmentPatient findBySlotIdAndPatientIdAndDate(long slotId, long patientId, String date);
    List<AppointmentPatient> findByPatientName(String name);
    List<AppointmentPatient> findByPatientId(long patientId);
    List<AppointmentPatient> findByDentistServices_AccountId(long id);
}
