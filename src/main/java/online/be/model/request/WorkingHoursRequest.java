package online.be.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import online.be.enums.Status;

@Getter
@Setter
public class WorkingHoursRequest {
    String date;
    String startTime;
    String endTime;
    @Enumerated(EnumType.STRING)
    Status status;
}
