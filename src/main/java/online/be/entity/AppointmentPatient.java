package online.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import online.be.enums.Status;

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


    @ManyToOne
    @JoinColumn(name = "dentistServices_id")
    DentistServices dentistServices;

    @Enumerated(EnumType.STRING)
    Status status;

    @JsonIgnore
    @OneToOne(mappedBy = "appointmentPatient")
    CheckIn checkIns;

    @JsonIgnore
    @OneToMany(mappedBy = "appointmentPatient")
    List<MedicalRecord> medicalRecords;

}
