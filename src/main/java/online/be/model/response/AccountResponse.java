package online.be.model.response;

import lombok.Data;
import online.be.entity.Account;

@Data
public class AccountResponse extends Account {
    String token;
}
