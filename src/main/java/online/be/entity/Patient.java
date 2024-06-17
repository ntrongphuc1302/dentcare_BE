package online.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import online.be.enums.PatientEnum;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    String age;

    Boolean gender;

    String address;

    String phoneNumber;

    String email;

    @Enumerated(EnumType.STRING)
    PatientEnum patientEnum;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    List<AppointmentPatient> appointmentPatients;




}
