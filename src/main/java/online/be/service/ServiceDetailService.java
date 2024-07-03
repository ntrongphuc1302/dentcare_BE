package online.be.service;

import online.be.entity.ServiceDetail;
import online.be.enums.ServiceDetailEnum;
import online.be.exception.DuplicateException;
import online.be.exception.NotFoundException;
import online.be.model.request.ServiceDetailRequest;
import online.be.model.request.ServiceDetailUpdateRequest;
import online.be.repository.ServiceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceDetailService {

    @Autowired
    private ServiceDetailRepository serviceDetailRepository;

    public List<ServiceDetail> getAllServiceDetail() {
        return serviceDetailRepository.findAllByServiceDetailEnum(ServiceDetailEnum.ACTIVE);
    }

    public ServiceDetail getServiceById(long id) {
        return serviceDetailRepository.findById(id);
    }

    public List<ServiceDetail> getServiceByKeyName(String key) {
        return serviceDetailRepository.findByNameContaining(key);
    }

    public ServiceDetail createService(ServiceDetailRequest serviceDetailRequest) {
        ServiceDetail service = new ServiceDetail();
        service.setName(serviceDetailRequest.getName());
        service.setPrice(serviceDetailRequest.getPrice());
        service.setDescription(serviceDetailRequest.getDescription());
        service.setServiceDetailEnum(ServiceDetailEnum.ACTIVE);
        try {
            return serviceDetailRepository.save(service);
        } catch (Exception e) {
            throw new DuplicateException("Service already exists!");
        }
    }

    public ServiceDetail updateDentistService(ServiceDetailUpdateRequest serviceDetailUpdateRequest) {
        ServiceDetail service = serviceDetailRepository.findById(serviceDetailUpdateRequest.getId());
        if (service != null) {
            service.setName(serviceDetailUpdateRequest.getName());
            service.setPrice(serviceDetailUpdateRequest.getPrice());
            service.setDescription(serviceDetailUpdateRequest.getDescription());
            service.setServiceDetailEnum(serviceDetailUpdateRequest.getServiceDetailEnum());
            return serviceDetailRepository.save(service);
        } else {
            throw new NotFoundException("Dentist Service not found!");
        }
    }

    public void deleteDentistService(long id) {
        ServiceDetail service = serviceDetailRepository.findById(id);
        if (service != null) {
            service.setServiceDetailEnum(ServiceDetailEnum.INACTIVE);
            serviceDetailRepository.save(service);
        } else {
            throw new NotFoundException("Dentist Service not found!");
        }
    }

}
