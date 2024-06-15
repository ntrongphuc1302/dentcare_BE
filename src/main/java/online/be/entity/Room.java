package online.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import online.be.enums.RoomEnum;

import java.util.List;

@Entity
@Getter
@Setter
@ToString

public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true)
    String name;

    @Enumerated(EnumType.STRING)
    RoomEnum roomEnum;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private DentalClinic clinic;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    List<Slot> slots;
}
