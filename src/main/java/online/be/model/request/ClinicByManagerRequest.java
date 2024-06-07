package online.be.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import online.be.enums.ClinicEnum;

@Getter
@Setter
public class ClinicByManagerRequest {
    long id;
    String clinicName;
    String address;
    String openHours;
    String closeHours;
    @Enumerated(EnumType.STRING)
    ClinicEnum clinicEnum;
}
