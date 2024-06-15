package online.be.repository;

import online.be.entity.ServiceDetail;
import online.be.enums.ServiceDetailEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceDetailRepository extends JpaRepository<ServiceDetail, Long> {
    ServiceDetail findById(long id);
    List<ServiceDetail> findAllByServiceDetailEnum(ServiceDetailEnum serviceDetailEnum);
    List<ServiceDetail> findServiceDetailsByDentalClinicsId(long id);
}
