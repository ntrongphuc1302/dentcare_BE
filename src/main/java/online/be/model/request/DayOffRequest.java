package online.be.model.request;

import lombok.Data;

@Data
public class DayOffRequest {
    long id;
    String dayOff;
    long dentistId;
    long slotId;
}
