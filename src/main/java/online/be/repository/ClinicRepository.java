package online.be.repository;

import online.be.entity.DentalClinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<DentalClinic, Long> {
    DentalClinic findById(long id);
}
