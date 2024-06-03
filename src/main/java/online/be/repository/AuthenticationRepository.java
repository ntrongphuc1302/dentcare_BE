package online.be.repository;

import online.be.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<Account, Long> {

    Account findAccountByEmail(String email);
}
