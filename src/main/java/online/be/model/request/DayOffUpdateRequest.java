package online.be.model.request;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class DayOffUpdateRequest {
    long id;
    LocalDate dayOff;
    long dentistId;
    long slotId;
}
