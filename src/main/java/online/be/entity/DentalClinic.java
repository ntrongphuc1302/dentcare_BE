package online.be.entity;

import jakarta.mail.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import online.be.enums.ClinicEnum;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class DentalClinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String clinicName;

    @Column(unique = true)
    String address;

    String openHours;

    String closeHours;

    @Enumerated(EnumType.STRING)
    ClinicEnum clinicEnum;

    @OneToMany(mappedBy = "dentalClinic")
    private List<Account> accounts;
}
