package online.be.entity;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String checkInTime;

    boolean isCheckIn;

    boolean isCancel;

    @OneToOne
    @JoinColumn(name = "appointment_patient_id")
    AppointmentPatient appointmentPatient;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    Account account;
}
