package online.be.service;

import online.be.entity.DentalClinic;
import online.be.entity.ServiceDetail;
import online.be.exception.NotFoundException;
import online.be.model.request.ServiceClinicRequest;
import online.be.repository.ClinicRepository;
import online.be.repository.ServiceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceClinicService {

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    ServiceDetailRepository serviceDetailRepository;

    public List<ServiceDetail> getAllServiceDetailsByDentalClinicId(long id) {
        return serviceDetailRepository.findServiceDetailsByDentalClinicsId(id);
    }

    public List<DentalClinic> getAllDentalClinicsByServiceDetailId(long id) {
        return clinicRepository.findDentalClinicsByServiceDetailsId(id);
    }

    public List<DentalClinic> getAllDentalClinicsByServiceDetailName(String name) {
        return clinicRepository.findDentalClinicsByServiceDetailsName(name);
    }

    public void enrollServiceInClinic(ServiceClinicRequest serviceClinicRequest) {
        ServiceDetail serviceDetail = serviceDetailRepository.findById(serviceClinicRequest.getServiceId());

        DentalClinic clinic = clinicRepository.findById(serviceClinicRequest.getClinicId()).
                orElseThrow(() -> new NotFoundException("Clinic not found"));

        if (serviceDetail != null) {
            serviceDetail.getDentalClinics().add(clinic);
        } else {
            throw new NotFoundException("Service not found");
        }

        clinic.getServiceDetails().add(serviceDetail);

        serviceDetailRepository.save(serviceDetail);
        clinicRepository.save(clinic);
    }

    public void removeServiceInClinic(ServiceClinicRequest serviceClinicRequest) {
        ServiceDetail serviceDetail = serviceDetailRepository.findById(serviceClinicRequest.getServiceId());

        DentalClinic clinic = clinicRepository.findById(serviceClinicRequest.getClinicId()).
                orElseThrow(() -> new NotFoundException("Clinic not found"));

        if (serviceDetail != null) {
            serviceDetail.getDentalClinics().remove(clinic);
        } else {
            throw new NotFoundException("Service not found");
        }

        clinic.getServiceDetails().remove(serviceDetail);

        serviceDetailRepository.save(serviceDetail);
        clinicRepository.save(clinic);
    }
}
