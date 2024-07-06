package online.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.be.entity.Account;
import online.be.model.request.*;
import online.be.model.response.AccountResponse;
import online.be.service.AuthenticationService;
import online.be.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
public class AuthenticationAPI {

    // nhận request từ front-end

    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("test") // /api/test
    public ResponseEntity test() {
        return ResponseEntity.ok("test");
    }

    @GetMapping("admin-only") // /api/admin-only
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getAdmin() {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("register") // /api/register
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {
        // account đã add xuống db
        Account account = authenticationService.register(registerRequest);
        return ResponseEntity.ok(account);
    }


    @PostMapping("register-by-admin") // /api/register
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
//    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity register(@RequestBody AdminRegisterRequest adminRegisterRequest) {
        // account đã add xuống db
        Account account = authenticationService.registerAdmin(adminRegisterRequest);
        return ResponseEntity.ok(account);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        AccountResponse account = authenticationService.login(loginRequest);
        return ResponseEntity.ok(account);
    }

    @PostMapping("login-google")
    public ResponseEntity<AccountResponse> loginGoogle(@RequestBody LoginGoogleRequest loginGoogleRequest){
        return ResponseEntity.ok(authenticationService.loginGoogle(loginGoogleRequest));
    }


    @PostMapping("forgot-password")
    public void forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authenticationService.forgotPasswordRequest(forgotPasswordRequest.getEmail());
    }

    @PatchMapping("reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        authenticationService.resetPassword(resetPasswordRequest);
    }

    @GetMapping("account")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getAllAccount() {
        return ResponseEntity.ok(authenticationService.getAllAccount());
    }
}
