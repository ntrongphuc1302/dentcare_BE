package online.be.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import online.be.entity.Account;
import online.be.entity.DentalClinic;
import online.be.entity.Room;
import online.be.enums.Role;
import online.be.enums.Status;
import online.be.exception.BadRequestException;
import online.be.exception.NotFoundException;
import online.be.model.*;
import online.be.model.request.*;
import online.be.model.response.AccountResponse;
import online.be.repository.AuthenticationRepository;
import online.be.repository.ClinicRepository;
import online.be.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    AuthenticationManager authenticationManager;

    // xử lý logic
    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    @Autowired
    EmailService emailService;


    public Account register(RegisterRequest registerRequest) {
        //registerRequest: thông tin ngừoi dùng yêu cầu

        // xử lý logic register
        Account account = new Account();

        account.setPhone(registerRequest.getPhone());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setRole(Role.CUSTOMER);
        account.setEmail(registerRequest.getEmail());
        account.setFullName(registerRequest.getFullName());
        account.setStatus(Status.ACTIVE);

        try {
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(account.getEmail());
            emailDetail.setFullName(account.getFullName());
            emailDetail.setSubject("You are invited to system!");
            emailDetail.setMsgBody("aaa");
            emailDetail.setButtonValue("Login to system");
            emailDetail.setLink("http://dentcare.website/login");
            emailService.sendMailTemplate(emailDetail);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // nhờ repo => save xuống db
        return authenticationRepository.save(account);
    }

    public Account registerAdmin(AdminRegisterRequest adminRegisterRequest) {
        //registerRequest: thông tin ngừoi dùng yêu cầu

        // xử lý logic register
        Account account = new Account();

        account.setPhone(adminRegisterRequest.getPhone());
        account.setPassword(passwordEncoder.encode(adminRegisterRequest.getPassword()));
        account.setRole(adminRegisterRequest.getRole());
        account.setEmail(adminRegisterRequest.getEmail());
        account.setFullName(adminRegisterRequest.getFullName());
        account.setStatus(Status.ACTIVE);
        // Gán Clinic ID cho tài khoản từ yêu cầu đăng ký
        DentalClinic clinic = null;
        Room room = null;
        if (adminRegisterRequest.getRole() == Role.MANAGER ||
            adminRegisterRequest.getRole() == Role.STAFF ||
            adminRegisterRequest.getRole() == Role.DENTIST) {
            clinic = clinicRepository.findById(adminRegisterRequest.getClinicId())
                    .orElseThrow(() -> new NotFoundException("Cannot find this clinicId"));

        }
        if (adminRegisterRequest.getRole() == Role.DENTIST) {
            room = roomRepository.findById(adminRegisterRequest.getRoomId());
            if (room != null) {
                account.setRoom(room);
            } else {
                throw new NotFoundException("Cannot find this Room");
            }
        }

        account.setDentalClinic(clinic);

        try {
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(account.getEmail());
            emailDetail.setFullName(account.getFullName());
            emailDetail.setSubject("You are invited to system!");
            emailDetail.setMsgBody("aaa");
            emailDetail.setButtonValue("Login to system");
            emailDetail.setLink("http://dentcare.website/login");
            emailService.sendMailTemplate(emailDetail);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // nhờ repo => save xuống db
        return authenticationRepository.save(account);
    }

    public AccountResponse loginGoogle(LoginGoogleRequest loginGoogleRequest){
        AccountResponse accountResponse = new AccountResponse();
        try{
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(loginGoogleRequest.getToken());
            String email = firebaseToken.getEmail();
            Account account = authenticationRepository.findAccountByEmail(email);
            if(account == null){
                account = new Account();
                account.setFullName(firebaseToken.getName());
                account.setEmail(firebaseToken.getEmail());
                account.setRole(Role.CUSTOMER);
                account = authenticationRepository.save(account);
            }
            accountResponse.setFullName(account.getFullName());
            accountResponse.setEmail(account.getEmail());
            accountResponse.setRole(account.getRole());
            accountResponse.setId(account.getId());
            accountResponse.setPhone(account.getPhone());
            accountResponse.setToken(tokenService.generateToken(account));
            accountResponse.setRole(account.getRole());
            accountResponse.setDentalClinic(account.getDentalClinic());
//            return accountResponse;

        }catch (FirebaseAuthException e){
            e.printStackTrace();
        }
        return accountResponse;
    }

    public AccountResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));
        // => account chuẩn

        Account account = authenticationRepository.findAccountByEmail(loginRequest.getEmail());
        String token = tokenService.generateToken(account);

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setPhone(account.getPhone());
        accountResponse.setToken(token);
        accountResponse.setEmail(account.getEmail());
        accountResponse.setFullName(account.getFullName());
        accountResponse.setRole(account.getRole());
        accountResponse.setId(account.getId());
        accountResponse.setDentalClinic(account.getDentalClinic());
        return accountResponse;
    }

    public void forgotPasswordRequest(String email) {
        Account account = authenticationRepository.findAccountByEmail(email);
        if (account == null) {
            try {
                throw new BadRequestException("Account not found!");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }

        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(account.getEmail());
        emailDetail.setFullName(account.getFullName());
        emailDetail.setSubject("Reset password for account " + account.getEmail() + "!");
        emailDetail.setMsgBody("aaa");
        emailDetail.setButtonValue("Reset Password");
        emailDetail.setLink("http://dentcare.website/reset?token=" + tokenService.generateToken(account));
        emailService.sendMailTemplate(emailDetail);
    }

    public Account resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Account account = getCurrentAccount();
        account.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        return authenticationRepository.save(account);
    }


    public List<Account> getAllAccount() {
        return authenticationRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authenticationRepository.findAccountByEmail(email);
    }

    public Account getCurrentAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // Assuming we have a method like this in AuthenticationService
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
