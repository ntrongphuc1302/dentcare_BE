package online.be.repository;

import online.be.entity.AppointmentPatient;
import online.be.entity.Patient;
import online.be.entity.Slot;
import online.be.enums.CheckInStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentPatientRepository extends JpaRepository<AppointmentPatient, Long> {
    AppointmentPatient findById(long id);
    AppointmentPatient findBySlotIdAndPatientIdAndDate(long slotId, long patientId, LocalDate date);
    List<AppointmentPatient> findByPatientName(String name);
    List<AppointmentPatient> findByPatientId(long patientId);
    List<AppointmentPatient> findByDentistServices_AccountId(long id);
    List<AppointmentPatient> findByPatient_PhoneNumber(String phone);
    List<AppointmentPatient> findBySlotIdAndDentistServices_AccountId(long sId, long dId);
    List<AppointmentPatient> findByPatientIdAndDentistServices_AccountIdAndDate(long patientId, long denId, LocalDate date);

    List<AppointmentPatient> findByPatientIdAndDate(long id, LocalDate date);
    List<AppointmentPatient> findByDate(LocalDate date);

    List<AppointmentPatient> findByAccountId(long id);
    List<AppointmentPatient> findByDentistServices_Account_DentalClinicId(long id);
    List<AppointmentPatient> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<AppointmentPatient> findByDateBetweenAndDentistServices_AccountId(LocalDate startDate, LocalDate endDate, long id);
    List<AppointmentPatient> findByDateBetweenAndDentistServices_Account_DentalClinicId
            (LocalDate startDate, LocalDate endDate, long id);
    List<AppointmentPatient> findByDentistServices_Account_DentalClinicIdAndDate(long id, LocalDate date);
    List<AppointmentPatient> findByStatus(CheckInStatus status);

}
