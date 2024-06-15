package online.be.service;


import online.be.entity.DentistServices;
import online.be.enums.Role;
import online.be.enums.Status;
import online.be.exception.DuplicateException;
import online.be.exception.InvalidRoleException;
import online.be.exception.NotFoundException;
import online.be.model.request.DentistServiceRequest;
import online.be.repository.AccountRepository;
import online.be.repository.DentistServiceRepository;
import online.be.repository.ServiceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentistServiceService {

    @Autowired
    DentistServiceRepository dentistServiceRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ServiceDetailRepository serviceDetailRepository;

    public List<DentistServices> getAllDentistService() {
        return dentistServiceRepository.findAll();
    }

    public DentistServices getDentistServiceById(long id) {
        return dentistServiceRepository.findById(id);
    }

    public List<DentistServices> getAllDentistByServiceId(long id) {
        return dentistServiceRepository.findAllAccountByServiceDetailId(id);
    }

    public List<DentistServices> getAllServiceByDentistId(long id) {
        var account = accountRepository.findById(id);
        if (account.getRole() == Role.DENTIST) {
            return dentistServiceRepository.findAllServiceDetailByAccountId(id);
        } else {
            throw new InvalidRoleException("The " + account.getRole() + " role is invalid");
        }
    }

    public DentistServices createDentistService(DentistServiceRequest dentistServiceRequest) {
        DentistServices dentistServices = dentistServiceRepository.
                findByAccountIdAndServiceDetailId(dentistServiceRequest.getDentistId(),
                        dentistServiceRequest.getServiceId());
        if (dentistServices == null) {
            dentistServices = new DentistServices();
            var account = accountRepository.findById(dentistServiceRequest.getDentistId());
            if (account.getRole() == Role.DENTIST) {
                dentistServices.setAccount(account);
            } else {
                throw new InvalidRoleException("The " + account.getRole() + " role is invalid");
            }
            var service = serviceDetailRepository.findById(dentistServiceRequest.getServiceId());
            dentistServices.setServiceDetail(service);
            dentistServices.setStatus(Status.ACTIVE);
            return dentistServiceRepository.save(dentistServices);
        } else {
            throw new DuplicateException("These ID have been existed");
        }
    }

    public DentistServices updateDentistService(DentistServiceRequest dentistServiceRequest) {
//        DentistServices dentistServices = dentistServiceRepository.
//                findByAccountIdAndServiceDetailId(dentistServiceRequest.getDentistId(), dentistServiceRequest.getServiceId());

        DentistServices dentistServices = dentistServiceRepository.findById(dentistServiceRequest.getId());
        if (dentistServices != null) {

            var account = accountRepository.findById(dentistServiceRequest.getDentistId());
            dentistServices.setAccount(account);
            var service = serviceDetailRepository.findById(dentistServiceRequest.getServiceId());
            dentistServices.setServiceDetail(service);
//            dentistServices.setStatus(dentistServiceRequest.getStatus());
            return dentistServiceRepository.save(dentistServices);
        } else {
            throw new NotFoundException("These ID have not been existed");
        }
    }

    public void changeStatus(long id) {
        DentistServices dentistServices = dentistServiceRepository.findById(id);

        if (dentistServices != null) {
            dentistServices.setStatus(Status.ACTIVE);
            dentistServiceRepository.save(dentistServices);
        } else {
            throw new NotFoundException("These ID have not been existed");
        }
    }

    public void deleteDentistService(DentistServiceRequest dentistServiceRequest) {
        DentistServices dentistServices = dentistServiceRepository.
                findByAccountIdAndServiceDetailId(dentistServiceRequest.getDentistId(), dentistServiceRequest.getServiceId());
        if (dentistServices != null) {

            dentistServices.setStatus(Status.INACTIVE);
            dentistServiceRepository.save(dentistServices);
        } else {
            throw new NotFoundException("These ID have not been existed");
        }
    }
}
