package online.be.model.request;

import lombok.Data;
//import online.be.enums.Role;

@Data
public class RegisterRequest {
    String phone;
    String password;
    String fullName;
    String email;
}
