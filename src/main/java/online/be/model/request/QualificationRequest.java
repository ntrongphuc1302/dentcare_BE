package online.be.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import online.be.enums.QualificationEnum;
//import online.be.enums.Status;

@Data
public class QualificationRequest {
    long id;
    String name;
    String institution;
    int yearObtained;
    String description;
    @Enumerated(EnumType.STRING)
    QualificationEnum qualificationEnum;
}
