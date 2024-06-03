package online.be.model;

import lombok.Data;
import online.be.enums.Role;

@Data
public class RegisterRequest {
    String phone;
    String password;
    String fullName;
    String email;
    Role role;
}
