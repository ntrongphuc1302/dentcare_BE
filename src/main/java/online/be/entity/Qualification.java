package online.be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import online.be.enums.QualificationEnum;
//import online.be.enums.Status;

@Entity
@Getter
@Setter
@ToString
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    String institution; // tổ chức cấp bằng

    int yearObtained; //năm nhận bằng

    String description;

    @Enumerated(EnumType.STRING)
    QualificationEnum qualificationEnum;

    @ManyToOne
    @JoinColumn(name = "dentist_id")
    Account account;

}
