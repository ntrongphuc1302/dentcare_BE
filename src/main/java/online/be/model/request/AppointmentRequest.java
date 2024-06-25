package online.be.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import online.be.enums.CheckInStatus;
import online.be.enums.Status;

@Data
public class AppointmentRequest {

    long id;
    long slotId;
    long patientId;
    long dentistServiceId;
    String date;
    long staffId;
    long cusId;
    @Enumerated(EnumType.STRING)
    CheckInStatus status;
}
