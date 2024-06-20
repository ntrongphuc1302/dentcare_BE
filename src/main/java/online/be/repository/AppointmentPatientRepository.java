package online.be.repository;

import online.be.entity.AppointmentPatient;
import online.be.entity.Patient;
import online.be.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentPatientRepository extends JpaRepository<AppointmentPatient, Long> {
    AppointmentPatient findById(long id);
    AppointmentPatient findBySlotIdAndPatientId(long slotId, long patientId);
    List<AppointmentPatient> findByPatientName(String name);
}
