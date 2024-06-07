package online.be.repository;

import online.be.entity.DentalClinic;
import online.be.enums.ClinicEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicRepository extends JpaRepository<DentalClinic, Long> {
    DentalClinic findById(long id);
    List<DentalClinic> findAllByClinicEnum(ClinicEnum clinicEnum);
}
