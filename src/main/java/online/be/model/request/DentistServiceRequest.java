package online.be.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import online.be.enums.Status;

@Data
public class DentistServiceRequest {
    long id;
    long dentistId;
    long serviceId;
    @Enumerated(EnumType.STRING)
    Status status;
}
