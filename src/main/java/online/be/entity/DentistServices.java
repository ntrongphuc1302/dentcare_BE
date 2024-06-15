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
public class DentistServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;


    @ManyToOne
    @JoinColumn(name = "dentist_id")
    Account account;


    @ManyToOne
    @JoinColumn(name = "service_id")
    ServiceDetail serviceDetail;

    @JsonIgnore
    @OneToMany(mappedBy = "dentistServices")
    List<AppointmentPatient> appointmentPatients;

    @Enumerated(EnumType.STRING)
    Status status;

}
