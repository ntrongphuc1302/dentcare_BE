package online.be.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalRecordUpdateRequest {
    long id;
    String note;
    String diagnosis;
}
