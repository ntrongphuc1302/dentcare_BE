package online.be.repository;

import online.be.entity.Qualification;
import online.be.enums.QualificationEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    Qualification findById(long id);
//    Qualification findByName(String name);
    List<Qualification> findAllByQualificationEnum(QualificationEnum qualificationEnum);
    List<Qualification> findByAccountId(long id);
}
