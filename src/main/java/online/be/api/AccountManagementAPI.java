package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.enums.Role;
import online.be.model.request.AccountRequest;
import online.be.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity getAccountByRoleAndClinic(@PathVariable Role role, @PathVariable long clinicId) {
        return ResponseEntity.ok(accountService.getAccountByRoleAndClinic(role, clinicId));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity getAccountByRole(@PathVariable Role role) {
        return ResponseEntity.ok(accountService.getAccountByRole(role));
    }

    @GetMapping("/{id}")
    public ResponseEntity getAccountById(@PathVariable long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping("/search-by-name/{name}")
    public ResponseEntity getAccountByName(@PathVariable String name) {
        return ResponseEntity.ok(accountService.getAccountByName(name));
    }

    @GetMapping("role/DENTIST/clinic/{clinicId}/service/{serviceId}")
    public ResponseEntity getAccountByRoleAndClinicAndService(@PathVariable long clinicId,@PathVariable long serviceId) {
        return ResponseEntity.ok(accountService.
                getAccountByRoleAndClinicAndService(Role.DENTIST, clinicId, serviceId));
    }

//    @GetMapping("/rooms/dentist/{id}")
//    public ResponseEntity getRoomsByDentistId(@PathVariable long id) {
//        return ResponseEntity.ok(accountService.getRoomsByDentistId(id));
//    }

    @PutMapping
    public ResponseEntity updateAccount(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity.ok(accountService.updateAccount(accountRequest));
    }

    @DeleteMapping
    public ResponseEntity deleteAccount(@RequestBody AccountRequest accountRequest) {
        accountService.deleteAccount(accountRequest);
        return ResponseEntity.ok("Account " + accountRequest.getId() + "has been deleted");
    }
}
