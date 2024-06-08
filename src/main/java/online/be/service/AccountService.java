package online.be.service;

import online.be.entity.Account;
import online.be.enums.Role;
import online.be.exception.NotFoundException;
import online.be.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAccountByRoleAndClinic(Role role, long id) {
        return accountRepository.findAccountsByRoleAndDentalClinicId(role, id);
    }



    public Account getAccountById(long id) {
        try {
            return accountRepository.findById(id);
        } catch (Exception e) {
            throw new NotFoundException("Account not found with id " + id);
        }
    }


}
