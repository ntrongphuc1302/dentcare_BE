package online.be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import online.be.enums.Status;

@Entity
@Getter
@Setter
@ToString
public class TreatmentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    String description;

    String frequency;

    @Enumerated(EnumType.STRING)
    Status status;

    @ManyToOne
    @JoinColumn(name = "record_id")
    MedicalRecord medicalRecord;
}
