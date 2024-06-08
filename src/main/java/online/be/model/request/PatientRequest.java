package online.be.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PatientRequest {
    long id;
    String name;
    String age;
    Boolean gender;
    String address;
    String phoneNumber;
    String email;

    long accountId;
}
