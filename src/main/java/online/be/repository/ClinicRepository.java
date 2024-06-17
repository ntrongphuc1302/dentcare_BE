package online.be.repository;

import online.be.entity.DentalClinic;
import online.be.enums.ClinicEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<DentalClinic, Long> {
//    DentalClinic findById(long id);
    List<DentalClinic> findAllByClinicEnum(ClinicEnum clinicEnum);
    List<DentalClinic> findDentalClinicsByServiceDetailsId(long id);
    List<DentalClinic> findDentalClinicsByServiceDetailsName(String name);
}
