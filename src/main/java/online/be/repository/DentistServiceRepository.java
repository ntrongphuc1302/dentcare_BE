package online.be.repository;

import online.be.entity.DentistServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DentistServiceRepository extends JpaRepository<DentistServices, Long> {
    DentistServices findById(long id);
    DentistServices findByAccountIdAndServiceDetailId(long dentistId, long serviceId);
    DentistServices findByAccountId(long id);
    DentistServices findByServiceDetailId(long id);
    List<DentistServices> findAllAccountByServiceDetailId(long id);
    List<DentistServices> findAllServiceDetailByAccountId(long id);

}
