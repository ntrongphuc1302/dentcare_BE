package online.be.service;

import online.be.entity.Account;
import online.be.enums.Role;
import online.be.exception.NotFoundException;
import online.be.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentistService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllDentist() {
        return accountRepository.findAllByRole(Role.DENTIST);
    }

    public Account getDentistById(long id) {
        if(accountRepository.findById(id).getRole() == Role.DENTIST) {
            return accountRepository.findById(id);
        } else {
            throw new NotFoundException("Dentist not found with id " + id);
        }
    }
}
