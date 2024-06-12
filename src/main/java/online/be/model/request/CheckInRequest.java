package online.be.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckInRequest {
    String checkInTime;
    boolean isCheckIn;
    boolean isCancel;
}
