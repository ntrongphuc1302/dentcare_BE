package online.be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class WorkingDayOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String dayOff;

    @ManyToOne
    @JoinColumn(name = "dentist_id")
    Account account;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    Slot slot;
}
