package online.be.model;

import lombok.Data;
import online.be.entity.Account;

@Data
public class AccountResponse extends Account {
    String token;
}
