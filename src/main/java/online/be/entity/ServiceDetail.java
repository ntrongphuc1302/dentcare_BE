package online.be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import online.be.enums.ServiceDetailEnum;

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


    @OneToMany(mappedBy = "serviceDetail")
    List<DentistService> dentistServiceLists;





}
