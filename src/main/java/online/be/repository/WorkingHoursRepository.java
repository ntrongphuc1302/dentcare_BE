package online.be.repository;

import online.be.entity.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {
}
