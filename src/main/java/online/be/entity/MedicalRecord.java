package online.be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import online.be.enums.MedicalRecordEnum;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String note;

    String diagnosis;

    @Enumerated(EnumType.STRING)
    MedicalRecordEnum medicalRecordEnum;

    @ManyToOne
    @JoinColumn(name = "appointment_patient_id")
    AppointmentPatient appointmentPatient;

    @OneToMany(mappedBy = "medicalRecord")
    List<TreatmentPlan> treatmentPlans;
}
