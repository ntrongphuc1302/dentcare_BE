package online.be.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RoomUpdateRequest {
    long id;
    String name;

    long clinicId;
}
