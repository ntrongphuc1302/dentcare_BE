package online.be.repository;

import online.be.entity.Slot;
import online.be.entity.WorkingDayOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    Slot findById(long id);

    Slot findSlotByName(String name);

    @Query("SELECT s FROM Slot s WHERE s.id NOT IN :excludedSlotIds")
    List<Slot> findAvailableSlotsExcluding(List<Long> excludedSlotIds);



//    List<Slot> findByAccountId(long id);

//    List<Slot> findByRoomId(long room_id);

//    List<Slot> findByDate(String date);
}
