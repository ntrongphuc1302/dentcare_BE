package online.be.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServiceClinicRequest {
    long serviceId;
    long clinicId;
}
