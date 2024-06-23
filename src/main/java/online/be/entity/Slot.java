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
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    String startTime;

    String endTime;

    int maxPatient;

//    String date;

//    Status status;

//    @ManyToOne
//    @JoinColumn(name = "dentist_id")
//    Account account;

//    @ManyToOne
//    @JoinColumn(name = "room_id")
//    Room room;

    @JsonIgnore
    @OneToMany(mappedBy = "slot")
    List<AppointmentPatient> appointmentPatients;

    @JsonIgnore
    @OneToMany(mappedBy = "slot")
    List<WorkingDayOff> workingDayOffs;
}
