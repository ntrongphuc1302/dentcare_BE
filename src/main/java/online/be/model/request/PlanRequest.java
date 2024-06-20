package online.be.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import online.be.enums.Status;

@Data
public class PlanRequest {
    long id;
    String name;
    String description;

    String frequency;
    @Enumerated(EnumType.STRING)
    Status status;
    long medicalId;
}
