package online.be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class DentistService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "dentist_id")
    Account account;


    @ManyToOne
    @JoinColumn(name = "service_id")
    ServiceDetail serviceDetail;




    @OneToMany(mappedBy = "dentistService")
    List<AppointmentPatient> appointmentPatients;

}
