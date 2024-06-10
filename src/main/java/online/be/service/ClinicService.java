package online.be.service;

import online.be.entity.DentalClinic;
import online.be.enums.ClinicEnum;
import online.be.exception.DuplicateException;
import online.be.exception.NotFoundException;
import online.be.model.request.ClinicByManagerRequest;
import online.be.model.request.ClinicRequest;
import online.be.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    public List<DentalClinic> getAllClinics() {
        return clinicRepository.findAllByClinicEnum(ClinicEnum.ACTIVE);
    }

    public DentalClinic getClinicById(Long id) {
        return clinicRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Clinic not found with id " + id));
    }

    public DentalClinic createClinic(ClinicRequest clinicRequest) {
        DentalClinic clinic =  new DentalClinic();
        clinic.setAddress(clinicRequest.getAddress());
        clinic.setClinicName(clinicRequest.getClinicName());
        clinic.setOpenHours(clinicRequest.getOpenHours());
        clinic.setCloseHours(clinicRequest.getCloseHours());
        clinic.setClinicEnum(ClinicEnum.ACTIVE);
        try {
            return clinicRepository.save(clinic);
        } catch (Exception e) {
            throw new DuplicateException("Address already exists!");
        }
    }

    public DentalClinic updateClinic(ClinicByManagerRequest clinicDetails) {
        DentalClinic clinic = clinicRepository.findById(clinicDetails.getId()).
                orElseThrow(() -> new NotFoundException("Clinic not found with id " + clinicDetails.getId()));
        if (clinic != null) {
            clinic.setClinicEnum(clinicDetails.getClinicEnum());
            clinic.setClinicName(clinicDetails.getClinicName());
            clinic.setAddress(clinicDetails.getAddress());
            clinic.setOpenHours(clinicDetails.getOpenHours());
            clinic.setCloseHours(clinicDetails.getCloseHours());
            return clinicRepository.save(clinic);
        } else {
            throw new NotFoundException("Clinic not found with id " + clinicDetails.getId());
        }
    }

    public void deleteClinic(Long id) {
        DentalClinic optionalClinic = clinicRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Clinic not found with id " + id));
        optionalClinic.setClinicEnum(ClinicEnum.INACTIVE);
        clinicRepository.save(optionalClinic);
    }
}
