package online.be.service;

import online.be.entity.Account;
import online.be.entity.Patient;
import online.be.enums.PatientEnum;
import online.be.exception.DuplicateException;
import online.be.exception.NotFoundException;
import online.be.model.request.PatientRequest;
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

    public List<Patient> getAllPatients() {
        return patientRepository.findAllByPatientEnum(PatientEnum.ACTIVE);
    }

    public Patient createPatient(PatientRequest patientRequest) {
        Account account = authenticationRepository.findById(patientRequest.getAccountId()).get();
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

    public Patient updatePatient(PatientRequest patientRequest) {
        Patient patient = patientRepository.findById(patientRequest.getId());
        if (patient != null) {
            patient.setName(patientRequest.getName());
            patient.setAge(patientRequest.getAge());
            patient.setGender(patientRequest.getGender());
            patient.setAddress(patientRequest.getAddress());
            patient.setPhoneNumber(patientRequest.getPhoneNumber());
            patient.setEmail(patientRequest.getEmail());
            return patientRepository.save(patient);
        } else {
            throw new NotFoundException("Patient not found with id " + patientRequest.getId());
        }
    }

    public void deletePatient(Long id) {
        Patient optionalPatient = patientRepository.findById(id).orElseThrow(() -> new NotFoundException("Patient not found with id " + id));
        optionalPatient.setPatientEnum(PatientEnum.INACTIVE);
        patientRepository.save(optionalPatient);
    }
}