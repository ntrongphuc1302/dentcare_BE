package online.be.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import online.be.enums.Role;

@Data
public class AdminRegisterRequest {
    String phone;
    String password;
    String fullName;
    String email;
    @Enumerated(EnumType.STRING)
    Role role;
    long clinicId;
    long roomId;
}
