package online.be.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import online.be.enums.ServiceDetailEnum;

@Getter
@Setter

public class ServiceDetailUpdateRequest {
    long id;
    String name;
    String price;
    String description;
    @Enumerated(EnumType.STRING)
    ServiceDetailEnum serviceDetailEnum;
}
