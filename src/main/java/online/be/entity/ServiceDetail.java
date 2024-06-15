package online.be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import online.be.enums.ServiceDetailEnum;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class ServiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true)
    String name;

    String price;

    String description;

    @Enumerated(EnumType.STRING)
    ServiceDetailEnum serviceDetailEnum;

    @JsonIgnore
    @OneToMany(mappedBy = "serviceDetail")
    List<DentistServices> dentistServicesLists;

    @ManyToMany
    @JoinTable(name = "service_clinic",
            joinColumns = @JoinColumn(name = "service_details_id"),
            inverseJoinColumns = @JoinColumn(name = "dental_clinics_id"))
    @JsonBackReference
    private Set<DentalClinic> dentalClinics = new HashSet<>();

}
