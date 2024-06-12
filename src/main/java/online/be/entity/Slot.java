package online.be.entity;

import jakarta.persistence.*;
import online.be.enums.Status;

import java.util.List;

@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    String startTime;

    String endTime;

    int MaxPatient;

    String Date;

    Status status;

    @ManyToOne
    @JoinColumn(name = "dentist_id")
    Account account;

    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;

    @OneToMany(mappedBy = "slot")
    List<AppointmentPatient> appointmentPatients;
}
