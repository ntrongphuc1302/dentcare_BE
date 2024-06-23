//package online.be.service;
//
//import online.be.entity.Account;
//import online.be.entity.DentalClinic;
//import online.be.enums.Role;
//import online.be.exception.NotFoundException;
//import online.be.model.EmailDetail;
//import online.be.model.request.*;
//import online.be.model.response.AccountResponse;
//import online.be.repository.AuthenticationRepository;
//import online.be.repository.ClinicRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.TestingAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AuthenticationServiceTest {
//
//    @Mock
//    private AuthenticationManager mockAuthenticationManager;
//    @Mock
//    private AuthenticationRepository mockAuthenticationRepository;
//    @Mock
//    private ClinicRepository mockClinicRepository;
//    @Mock
//    private PasswordEncoder mockPasswordEncoder;
//    @Mock
//    private TokenService mockTokenService;
//    @Mock
//    private EmailService mockEmailService;
//
//    private AuthenticationService authenticationServiceUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        authenticationServiceUnderTest = new AuthenticationService();
//        authenticationServiceUnderTest.authenticationManager = mockAuthenticationManager;
//        authenticationServiceUnderTest.authenticationRepository = mockAuthenticationRepository;
//        authenticationServiceUnderTest.clinicRepository = mockClinicRepository;
//        authenticationServiceUnderTest.passwordEncoder = mockPasswordEncoder;
//        authenticationServiceUnderTest.tokenService = mockTokenService;
//        authenticationServiceUnderTest.emailService = mockEmailService;
//    }
//
//    @Test
//    void testRegister() {
//        // Setup
//        final RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setPhone("phone");
//        registerRequest.setPassword("password");
//        registerRequest.setFullName("fullName");
//        registerRequest.setEmail("recipient");
//
//        when(mockPasswordEncoder.encode("password")).thenReturn("password");
//
//        // Configure AuthenticationRepository.save(...).
//        final Account account = new Account();
//        account.setId(0L);
//        account.setFullName("fullName");
//        account.setEmail("recipient");
//        account.setPhone("phone");
//        account.setPassword("password");
//        account.setRole(Role.ADMIN);
//        final DentalClinic dentalClinic = new DentalClinic();
//        account.setDentalClinic(dentalClinic);
//        when(mockAuthenticationRepository.save(any(Account.class))).thenReturn(account);
//
//        // Run the test
//        final Account result = authenticationServiceUnderTest.register(registerRequest);
//
//        // Verify the results
//        // Confirm EmailService.sendMailTemplate(...).
////        final EmailDetail emailDetail = new EmailDetail();
////        emailDetail.setRecipient("recipient");
////        emailDetail.setMsgBody("msgBody");
////        emailDetail.setSubject("subject");
////        emailDetail.setFullName("fullName");
////        emailDetail.setButtonValue("buttonValue");
////        emailDetail.setLink("link");
////        verify(mockEmailService).sendMailTemplate(emailDetail);
//    }
//
//    @Test
//    void testRegisterAdmin() {
//        // Setup
//        final AdminRegisterRequest adminRegisterRequest = new AdminRegisterRequest();
//        adminRegisterRequest.setPhone("phone");
//        adminRegisterRequest.setPassword("password");
//        adminRegisterRequest.setFullName("fullName");
//        adminRegisterRequest.setEmail("recipient");
//        adminRegisterRequest.setRole(Role.ADMIN);
//        adminRegisterRequest.setClinicId(0L);
//
//        when(mockPasswordEncoder.encode("password")).thenReturn("password");
//
//        // Configure ClinicRepository.findById(...).
//        final DentalClinic clinic = new DentalClinic();
//        clinic.setId(0L);
//        clinic.setClinicName("clinicName");
//        clinic.setAddress("address");
//        clinic.setOpenHours("openHours");
//        clinic.setCloseHours("closeHours");
//        final Optional<DentalClinic> dentalClinic = Optional.of(clinic);
////        when(mockClinicRepository.findById(0L)).thenReturn(dentalClinic);
//
//
//        // Configure AuthenticationRepository.save(...).
//        final Account account = new Account();
//        account.setId(0L);
//        account.setFullName("fullName");
//        account.setEmail("recipient");
//        account.setPhone("phone");
//        account.setPassword("password");
//        account.setRole(Role.ADMIN);
//        final DentalClinic dentalClinic1 = new DentalClinic();
//        account.setDentalClinic(dentalClinic1);
//        when(mockAuthenticationRepository.save(any(Account.class))).thenReturn(account);
//
//
//        // Run the test
//        final Account result = authenticationServiceUnderTest.registerAdmin(adminRegisterRequest);
//
//        // Verify the results
//        // Confirm EmailService.sendMailTemplate(...).
////        final EmailDetail emailDetail = new EmailDetail();
////        emailDetail.setRecipient("recipient");
////        emailDetail.setMsgBody("msgBody");
////        emailDetail.setSubject("subject");
////        emailDetail.setFullName("fullName");
////        emailDetail.setButtonValue("buttonValue");
////        emailDetail.setLink("link");
////        verify(mockEmailService).sendMailTemplate(emailDetail);
//    }
//
////    @Test
////    void testRegisterAdmin_ClinicRepositoryReturnsAbsent() {
////        // Setup
////        final AdminRegisterRequest adminRegisterRequest = new AdminRegisterRequest();
////        adminRegisterRequest.setPhone("phone");
////        adminRegisterRequest.setPassword("password");
////        adminRegisterRequest.setFullName("fullName");
////        adminRegisterRequest.setEmail("recipient");
////        adminRegisterRequest.setRole(Role.ADMIN);
////        adminRegisterRequest.setClinicId(0L);
////
////        when(mockPasswordEncoder.encode("password")).thenReturn("password");
////        when(mockClinicRepository.findById(0L)).thenReturn(Optional.empty());
////
////        // Run the test
////        assertThatThrownBy(() -> authenticationServiceUnderTest.registerAdmin(adminRegisterRequest))
////                .isInstanceOf(NotFoundException.class);
////    }
//
////    @Test
////    void testLoginGoogle() {
////        // Setup
////        final LoginGoogleRequest loginGoogleRequest = new LoginGoogleRequest();
////        loginGoogleRequest.setToken("Token");
////
////        final AccountResponse expectedResult = new AccountResponse();
////        expectedResult.setId(0L);
////        expectedResult.setFullName("fullName");
////        expectedResult.setEmail("recipient");
////        expectedResult.setPhone("phone");
////        expectedResult.setPassword("password");
////        expectedResult.setRole(Role.ADMIN);
////        final DentalClinic dentalClinic = new DentalClinic();
////        expectedResult.setDentalClinic(dentalClinic);
////        expectedResult.setToken("token");
////
////        // Configure AuthenticationRepository.findAccountByEmail(...).
////        final Account account = new Account();
////        account.setId(0L);
////        account.setFullName("fullName");
////        account.setEmail("recipient");
////        account.setPhone("phone");
////        account.setPassword("password");
////        account.setRole(Role.ADMIN);
////        final DentalClinic dentalClinic1 = new DentalClinic();
////        account.setDentalClinic(dentalClinic1);
////        when(mockAuthenticationRepository.findAccountByEmail("email")).thenReturn(account);
////
////        when(mockTokenService.generateToken(any(Account.class))).thenReturn("token");
////
////        // Run the test
////        final AccountResponse result = authenticationServiceUnderTest.loginGoogle(loginGoogleRequest);
////
////        // Verify the results
////        assertThat(result).isEqualTo(expectedResult);
////    }
//
////    @Test
////    void testLoginGoogle_AuthenticationRepositoryFindAccountByEmailReturnsNull() {
////        // Setup
////        final LoginGoogleRequest loginGoogleRequest = new LoginGoogleRequest();
////        loginGoogleRequest.setToken("Token");
////
////        final AccountResponse expectedResult = new AccountResponse();
////        expectedResult.setId(0L);
////        expectedResult.setFullName("fullName");
////        expectedResult.setEmail("recipient");
////        expectedResult.setPhone("phone");
////        expectedResult.setPassword("password");
////        expectedResult.setRole(Role.ADMIN);
////        final DentalClinic dentalClinic = new DentalClinic();
////        expectedResult.setDentalClinic(dentalClinic);
////        expectedResult.setToken("token");
////
////        when(mockAuthenticationRepository.findAccountByEmail("email")).thenReturn(null);
////
////        // Configure AuthenticationRepository.save(...).
////        final Account account = new Account();
////        account.setId(0L);
////        account.setFullName("fullName");
////        account.setEmail("recipient");
////        account.setPhone("phone");
////        account.setPassword("password");
////        account.setRole(Role.ADMIN);
////        final DentalClinic dentalClinic1 = new DentalClinic();
////        account.setDentalClinic(dentalClinic1);
////        when(mockAuthenticationRepository.save(any(Account.class))).thenReturn(account);
////
////        when(mockTokenService.generateToken(any(Account.class))).thenReturn("token");
////
////        // Run the test
////        final AccountResponse result = authenticationServiceUnderTest.loginGoogle(loginGoogleRequest);
////
////        // Verify the results
////        assertThat(result).isEqualTo(expectedResult);
////    }
//
//    @Test
//    void testLogin() {
//        // Setup
//        final LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setEmail("email");
//        loginRequest.setPassword("password");
//
//        final AccountResponse expectedResult = new AccountResponse();
//        expectedResult.setId(0L);
//        expectedResult.setFullName("fullName");
//        expectedResult.setEmail("recipient");
//        expectedResult.setPhone("phone");
//        expectedResult.setPassword("password");
//        expectedResult.setRole(Role.ADMIN);
//        final DentalClinic dentalClinic = new DentalClinic();
//        expectedResult.setDentalClinic(dentalClinic);
//        expectedResult.setToken("token");
//
//        // Configure AuthenticationRepository.findAccountByEmail(...).
//        final Account account = new Account();
//        account.setId(0L);
//        account.setFullName("fullName");
//        account.setEmail("recipient");
//        account.setPhone("phone");
//        account.setPassword("password");
//        account.setRole(Role.ADMIN);
//        final DentalClinic dentalClinic1 = new DentalClinic();
//        account.setDentalClinic(dentalClinic1);
//        when(mockAuthenticationRepository.findAccountByEmail("email")).thenReturn(account);
//
//        when(mockTokenService.generateToken(any(Account.class))).thenReturn("token");
//
//        // Run the test
//        final AccountResponse result = authenticationServiceUnderTest.login(loginRequest);
//
//        // Verify the results
//        assertThat(result).isEqualTo(expectedResult);
//        verify(mockAuthenticationManager).authenticate(new UsernamePasswordAuthenticationToken("email", "password"));
//    }
//
////    @Test
////    void testLogin_AuthenticationManagerThrowsAuthenticationException() {
////        // Setup
////        final LoginRequest loginRequest = new LoginRequest();
////        loginRequest.setEmail("email");
////        loginRequest.setPassword("password");
////
////        when(mockAuthenticationManager.authenticate(
////                new TestingAuthenticationToken("user", "pass", "ROLE_USER"))).thenThrow(AuthenticationException.class);
////
////        // Run the test
////        assertThatThrownBy(() -> authenticationServiceUnderTest.login(loginRequest))
////                .isInstanceOf(AuthenticationException.class);
////    }
//
//    @Test
//    void testForgotPasswordRequest() {
//        // Setup
//        // Configure AuthenticationRepository.findAccountByEmail(...).
//        final Account account = new Account();
//        account.setId(0L);
//        account.setFullName("fullName");
//        account.setEmail("recipient");
//        account.setPhone("phone");
//        account.setPassword("password");
//        account.setRole(Role.ADMIN);
//        final DentalClinic dentalClinic = new DentalClinic();
//        account.setDentalClinic(dentalClinic);
//        when(mockAuthenticationRepository.findAccountByEmail("email")).thenReturn(account);
//
//        when(mockTokenService.generateToken(any(Account.class))).thenReturn("result");
//
//        // Run the test
//        authenticationServiceUnderTest.forgotPasswordRequest("email");
//
//        // Verify the results
//        // Confirm EmailService.sendMailTemplate(...).
////        final EmailDetail emailDetail = new EmailDetail();
////        emailDetail.setRecipient("recipient");
////        emailDetail.setMsgBody("msgBody");
////        emailDetail.setSubject("subject");
////        emailDetail.setFullName("fullName");
////        emailDetail.setButtonValue("buttonValue");
////        emailDetail.setLink("link");
////        verify(mockEmailService).sendMailTemplate(emailDetail);
//    }
//
//    @Test
//    void testForgotPasswordRequest_AuthenticationRepositoryReturnsNull() {
//        // Setup
//        when(mockAuthenticationRepository.findAccountByEmail("email")).thenReturn(null);
//
//        // Run the test
//        assertThatThrownBy(() -> authenticationServiceUnderTest.forgotPasswordRequest("email"))
//                .isInstanceOf(RuntimeException.class);
//    }
//
////    @Test
////    void testResetPassword() {
////        // Setup
////        final ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
////        resetPasswordRequest.setPassword("password");
////
////        when(mockPasswordEncoder.encode("password")).thenReturn("password");
////
////        // Configure AuthenticationRepository.save(...).
////        final Account account = new Account();
////        account.setId(0L);
////        account.setFullName("fullName");
////        account.setEmail("recipient");
////        account.setPhone("phone");
////        account.setPassword("password");
////        account.setRole(Role.ADMIN);
////        final DentalClinic dentalClinic = new DentalClinic();
////        account.setDentalClinic(dentalClinic);
////        when(mockAuthenticationRepository.save(any(Account.class))).thenReturn(account);
////
////        // Run the test
////        final Account result = authenticationServiceUnderTest.resetPassword(resetPasswordRequest);
////
////        // Verify the results
////    }
//
//    @Test
//    void testGetAllAccount() {
//        // Setup
//        // Configure AuthenticationRepository.findAll(...).
//        final Account account = new Account();
//        account.setId(0L);
//        account.setFullName("fullName");
//        account.setEmail("recipient");
//        account.setPhone("phone");
//        account.setPassword("password");
//        account.setRole(Role.ADMIN);
//        final DentalClinic dentalClinic = new DentalClinic();
//        account.setDentalClinic(dentalClinic);
//        final List<Account> accounts = List.of(account);
//        when(mockAuthenticationRepository.findAll()).thenReturn(accounts);
//
//        // Run the test
//        final List<Account> result = authenticationServiceUnderTest.getAllAccount();
//
//        // Verify the results
//    }
//
//    @Test
//    void testGetAllAccount_AuthenticationRepositoryReturnsNoItems() {
//        // Setup
//        when(mockAuthenticationRepository.findAll()).thenReturn(Collections.emptyList());
//
//        // Run the test
//        final List<Account> result = authenticationServiceUnderTest.getAllAccount();
//
//        // Verify the results
//        assertThat(result).isEqualTo(Collections.emptyList());
//    }
//
//    @Test
//    void testLoadUserByUsername() {
//        // Setup
//        // Configure AuthenticationRepository.findAccountByEmail(...).
//        final Account account = new Account();
//        account.setId(0L);
//        account.setFullName("fullName");
//        account.setEmail("recipient");
//        account.setPhone("phone");
//        account.setPassword("password");
//        account.setRole(Role.ADMIN);
//        final DentalClinic dentalClinic = new DentalClinic();
//        account.setDentalClinic(dentalClinic);
//        when(mockAuthenticationRepository.findAccountByEmail("email")).thenReturn(account);
//
//        // Run the test
//        final UserDetails result = authenticationServiceUnderTest.loadUserByUsername("email");
//
//        // Verify the results
//    }
//
//    // Test case for logout
//    @Test
//    void testLogin_SuccessfulLogout() {
//        // Setup
//        SecurityContextHolder.getContext().setAuthentication(
//                new UsernamePasswordAuthenticationToken("user", "password")
//        );
//
//        // Run the test
//        authenticationServiceUnderTest.logout();
//
//        // Verify the results
//        assertNull(SecurityContextHolder.getContext().getAuthentication());
//    }
//
//    @Test
//    void testLogin_NonExistentEmailAddress() {
//        // Setup
//        final LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setEmail("nonexistent@example.com");
//        loginRequest.setPassword("password");
//
//        // Configure AuthenticationRepository.findAccountByEmail(...)
//        when(mockAuthenticationRepository.findAccountByEmail("nonexistent@example.com")).thenReturn(null);
//
//        // Run the test
//        assertThatThrownBy(() -> authenticationServiceUnderTest.login(loginRequest))
//                .isInstanceOf(NullPointerException.class)
//                .hasMessageContaining("Cannot invoke \"online.be.entity.Account.getPhone()\" because \"account\" is null");
//
//        // Verify the results
//        verify(mockAuthenticationRepository).findAccountByEmail("nonexistent@example.com");
//    }
//
//    @Test
//    void testLogin_IncorrectPassword() {
//        // Setup
//        final LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setEmail("user@example.com");
//        loginRequest.setPassword("incorrectPassword");
//
//        // Mock the behavior of authenticationManager to throw an exception for bad credentials
//        doThrow(new RuntimeException("Bad credentials")).when(mockAuthenticationManager).authenticate(any());
//
//        // Run the test
//        assertThatThrownBy(() -> authenticationServiceUnderTest.login(loginRequest))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessageContaining("Bad credentials");
//
//        // Verify the results
//        verify(mockAuthenticationManager).authenticate(any());
//    }
//
//
//}
