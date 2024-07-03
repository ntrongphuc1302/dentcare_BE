package online.be.repository;

import online.be.entity.Slot;
import online.be.entity.WorkingDayOff;
//import online.be.model.SlotIdCountDTO;
import online.be.model.SlotIdCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    Slot findById(long id);

    Slot findSlotByName(String name);

    @Query(value = "select a.slot_id, COUNT(a.slot_id) from appointment_patient a join dentist_services d ON d.id = a.dentist_services_id where d.dentist_id = :dentistId  and a.date = :date GROUP BY a.slot_id",nativeQuery = true)
    List<Object[]> findCountSlot(@Param("dentistId") long dentistId, @Param("date") LocalDate date);



    @Query("SELECT s FROM Slot s WHERE s.id IN :excludedSlotIds")
    List<Slot> findUnavailableSlotsExcluding(List<Long> excludedSlotIds);



//    List<Slot> findByAccountId(long id);

//    List<Slot> findByRoomId(long room_id);

//    List<Slot> findByDate(String date);
}
