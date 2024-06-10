package online.be.repository;

import online.be.entity.TreatmentPlan;
import online.be.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<TreatmentPlan, Long> {
    TreatmentPlan findById(long id);
    TreatmentPlan findByName(String name);
    List<TreatmentPlan> findAllByStatus(Status status);
}
