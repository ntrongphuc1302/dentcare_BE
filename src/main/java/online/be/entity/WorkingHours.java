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
public class WorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String date;

    String startTime;

    String endTime;

    Status status;

    @ManyToOne
    @JoinColumn(name = "dentist_id")
    Account account;
}
