package online.be.repository;

import online.be.entity.AppointmentPatient;
import online.be.entity.Patient;
import online.be.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentPatientRepository extends JpaRepository<AppointmentPatient, Long> {
    AppointmentPatient findById(long id);
    AppointmentPatient findBySlotIdAndPatientId(long slotId, long patientId);
}
