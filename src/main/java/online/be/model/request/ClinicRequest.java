package online.be.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicRequest {
    String clinicName;
    String address;
    String openHours;
    String closeHours;
}
