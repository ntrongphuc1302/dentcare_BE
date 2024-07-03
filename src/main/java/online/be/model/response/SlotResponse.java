package online.be.model.response;

import lombok.Data;

@Data
public class SlotResponse {
    long id;
    String name;
    String startTime;
    String endTime;
    int maxPatient;
    boolean isAvailable;
}
