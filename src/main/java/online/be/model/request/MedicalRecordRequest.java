package online.be.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalRecordRequest {
    String note;
    String diagnosis;
}
