package online.be.service;

import online.be.entity.Account;
import online.be.entity.DentalClinic;
import online.be.enums.ClinicEnum;
import online.be.enums.Role;
import online.be.enums.Status;
import online.be.exception.NotFoundException;
import online.be.model.request.AccountRequest;
import online.be.repository.AccountRepository;
import online.be.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    public List<Account> getAccountByRoleAndClinic(Role role, long id) {
        return accountRepository.findAccountsByRoleAndDentalClinicId(role, id);
    }

    public List<Account> getAccountByRole(Role role) {
        return accountRepository.findAccountsByRole(role);
    }

    public List<Account> getAccountByRoleAndClinicAndService(Role role, long clinicId, long serviceId) {
        return accountRepository.
                findByRoleAndDentalClinicIdAndDentistServices_ServiceDetailId(role, clinicId, serviceId);
    }

    public Account getAccountById(long id) {
        try {
            return accountRepository.findById(id);
        } catch (Exception e) {
            throw new NotFoundException("Account not found with id " + id);
        }
    }

    public List<Account> getAccountByName(String name) {
        return accountRepository.findAccountsByFullName(name);
    }

    public Account updateAccount(AccountRequest accountRequest) {
        Account account = accountRepository.findById(accountRequest.getId());
        if (account != null) {
            account.setFullName(accountRequest.getFullName());
            account.setPhone(accountRequest.getPhone());
            account.setRole(accountRequest.getRole());
            DentalClinic clinic = null;
            if (accountRequest.getRole() != Role.ADMIN) {
                clinic = clinicRepository.findById(accountRequest.getClinicID()).
                        orElseThrow(() -> new NotFoundException("Clinic not found with id " + accountRequest.getClinicID()));
            }
            
            account.setDentalClinic(clinic);
            return accountRepository.save(account);
        } else {
            throw new NotFoundException("Account not found with id " + accountRequest.getId());
        }
    }

    public void deleteAccount(AccountRequest accountRequest) {
        Account account =  accountRepository.findById(accountRequest.getId());
        if (account != null) { //check existed account
            account.setStatus(Status.INACTIVE);
            accountRepository.save(account);
        } else {
            throw new NotFoundException("Account not found with id " + accountRequest.getId());
        }

    }

}
