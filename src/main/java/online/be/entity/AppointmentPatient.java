package online.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.type.DateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import online.be.enums.CheckInStatus;
import online.be.enums.Status;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class AppointmentPatient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;


    @ManyToOne
    @JoinColumn(name = "slot_id")
    Slot slot;


    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    LocalDate date;

    @ManyToOne
    @JoinColumn(name = "dentistServices_id")
    DentistServices dentistServices;

    @Enumerated(EnumType.STRING)
    CheckInStatus status;

//    @JsonIgnore
//    @OneToOne(mappedBy = "appointmentPatient")
//    CheckIn checkIns;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    Account account;

    @ManyToOne
    @JoinColumn(name = "cus_id")
    Account account2;

    @JsonIgnore
    @OneToMany(mappedBy = "appointmentPatient")
    List<MedicalRecord> medicalRecords;

}
