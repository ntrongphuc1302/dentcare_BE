package online.be.repository;

import online.be.entity.Account;
import online.be.entity.Slot;
import online.be.entity.WorkingDayOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface WorkingDayOffRepository extends JpaRepository<WorkingDayOff, Long> {

    @Query("SELECT w.slot.id FROM WorkingDayOff w WHERE w.account.id = :dentistId AND w.dayOff = :dayOff")
    List<Long> findSlotIdsByDentistAndDayOff(long dentistId, LocalDate dayOff);

    WorkingDayOff findByAccountIdAndDayOff(long id, LocalDate date);
    WorkingDayOff findById(long id);
    List<WorkingDayOff> findByAccountId(long id);
    List<WorkingDayOff> findBySlotId(long id);

}
