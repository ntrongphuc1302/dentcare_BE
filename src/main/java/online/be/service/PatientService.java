package online.be.service;

import online.be.entity.Account;
import online.be.entity.Patient;
import online.be.enums.PatientEnum;
import online.be.exception.DuplicateException;
import online.be.exception.NotFoundException;
import online.be.model.request.PatientRequest;
import online.be.model.request.PatientUpdateRequest;
import online.be.repository.AuthenticationRepository;
import online.be.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    AuthenticationService authenticationService;

    public List<Patient> getAllPatients() {
        return patientRepository.findAllByPatientEnum(PatientEnum.ACTIVE);
    }

    public Patient getPatientByName(String name) {
        return patientRepository.findByName(name);
    }

    public Patient getPatientByPhone(String phone) {
        return patientRepository.findByPhoneNumber(phone);
    }

    public List<Patient> getPatientsByAccountId(long id) {
        return patientRepository.findByAccountIdAndPatientEnum(id, PatientEnum.ACTIVE);
    }

    public Patient createPatient(PatientRequest patientRequest) {
//        Account account = authenticationRepository.findById(patientRequest.getAccountId()).get();
        Account account = authenticationService.getCurrentAccount();
        Patient patient = new Patient();
        patient.setName(patientRequest.getName());
        patient.setAge(patientRequest.getAge());
        patient.setGender(patientRequest.getGender());
        patient.setAddress(patientRequest.getAddress());
        patient.setPhoneNumber(patientRequest.getPhoneNumber());
        patient.setEmail(patientRequest.getEmail());
        patient.setPatientEnum(PatientEnum.ACTIVE);
        patient.setAccount(account);

        try {
            return patientRepository.save(patient);
        } catch (Exception e) {
            throw new DuplicateException("Patient already exists!");
        }
    }

    public Patient updatePatient(PatientUpdateRequest patientUpdateRequest) {
        Patient patient = patientRepository.findById(patientUpdateRequest.getId());
        if (patient != null) {
            patient.setName(patientUpdateRequest.getName());
            patient.setAge(patientUpdateRequest.getAge());
            patient.setGender(patientUpdateRequest.getGender());
            patient.setAddress(patientUpdateRequest.getAddress());
            patient.setPhoneNumber(patientUpdateRequest.getPhoneNumber());
            patient.setEmail(patientUpdateRequest.getEmail());
            return patientRepository.save(patient);
        } else {
            throw new NotFoundException("Patient not found with id " + patientUpdateRequest.getId());
        }
    }

    public void deletePatient(Long id) {
        Patient optionalPatient = patientRepository.findById(id).orElseThrow(() -> new NotFoundException("Patient not found with id " + id));
        optionalPatient.setPatientEnum(PatientEnum.INACTIVE);
        patientRepository.save(optionalPatient);
    }
}