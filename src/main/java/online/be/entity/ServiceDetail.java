package online.be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import online.be.enums.ServiceDetailEnum;

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
}
