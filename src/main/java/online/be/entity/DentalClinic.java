package online.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.mail.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import online.be.enums.ClinicEnum;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class DentalClinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true)
    String clinicName;

    @Column(unique = true)
    String address;

    String openHours;

    String closeHours;

    @Enumerated(EnumType.STRING)
    ClinicEnum clinicEnum;

    @JsonIgnore
    @OneToMany(mappedBy = "dentalClinic")
    private List<Account> accounts;

    @JsonIgnore
    @OneToMany(mappedBy = "clinic")
    private List<Room> rooms;

    @ManyToMany(mappedBy = "dentalClinics")
    @JsonManagedReference
    @JsonIgnore
    private Set<ServiceDetail> serviceDetails = new HashSet<>();
}
