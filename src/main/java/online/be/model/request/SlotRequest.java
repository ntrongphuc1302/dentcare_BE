package online.be.model.request;

import lombok.Getter;
import lombok.Setter;
import online.be.enums.Status;

@Getter
@Setter
public class SlotRequest {
    String startTime;
    String endTime;
    int maxPatient;
    String date;
    Status status;
}
