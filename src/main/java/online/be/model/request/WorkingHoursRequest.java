package online.be.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkingHoursRequest {
    String date;
    String startTime;
    String endTime;
    String status;
}
