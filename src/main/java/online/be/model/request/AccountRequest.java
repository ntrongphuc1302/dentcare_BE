package online.be.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import online.be.enums.Role;

@Data
public class AccountRequest {
    long id;
    String fullName;
    String phone;
    @Enumerated(EnumType.STRING)
    Role role;
    long clinicID;
}
