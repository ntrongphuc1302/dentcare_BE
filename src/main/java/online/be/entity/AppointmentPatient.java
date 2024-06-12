package online.be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @JoinColumn(name = "dentistService_id")
    DentistService dentistService;

    @OneToOne(mappedBy = "appointmentPatient")
    CheckIn checkIns;

    @OneToMany(mappedBy = "appointmentPatient")
    List<MedicalRecord> medicalRecords;

}
