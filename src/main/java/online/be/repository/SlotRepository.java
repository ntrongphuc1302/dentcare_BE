package online.be.repository;

import online.be.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    Slot findById(long id);
    Slot findSlotByName(String name);
    List<Slot> findByAccountId(long id);
}
