package online.be.repository;

import online.be.entity.Account;
import online.be.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByEmail(String email);
    Account findById(long id);
    List<Account> findAccountsByRoleAndDentalClinicId(Role role, long id);
    List<Account> findAccountsByRole(Role role);
    List<Account> findByRoleAndDentalClinicIdAndDentistServices_ServiceDetailId(Role role, long clinicId, long serviceId);
    List<Account> findAccountsByFullName(String name);
}
