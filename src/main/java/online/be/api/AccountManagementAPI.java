package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.enums.Role;
import online.be.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/account")
@SecurityRequirement(name = "api")
public class AccountManagementAPI {

    @Autowired
    AccountService accountService;

//    @GetMapping
//    public ResponseEntity getAllClinics() {
//        return ResponseEntity.ok(accountService.getAllDentist());
//    }

    @GetMapping("/role/{role}/clinic/{clinicId}")
    public ResponseEntity getAccountByRoleAndClinic(@PathVariable Role role, @PathVariable long clinicId){
        return ResponseEntity.ok(accountService.getAccountByRoleAndClinic(role, clinicId));
    }

    @GetMapping("/{id}")
    public ResponseEntity getAccountById(@PathVariable long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
}
