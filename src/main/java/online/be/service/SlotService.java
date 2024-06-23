package online.be.service;

import online.be.entity.Slot;
import online.be.entity.WorkingDayOff;
import online.be.enums.Role;
import online.be.enums.Status;
import online.be.exception.DuplicateException;
import online.be.exception.InvalidRoleException;
import online.be.exception.NotFoundException;
import online.be.model.request.SlotRequest;
import online.be.model.request.SlotUpdateRequest;
import online.be.repository.AccountRepository;
import online.be.repository.RoomRepository;
import online.be.repository.SlotRepository;
import online.be.repository.WorkingDayOffRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private WorkingDayOffRepository workingDayOffRepository;

    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    public Slot findById(long id) {
        return slotRepository.findById(id);
    }


    public Slot getSlotByName(String name) {
        return slotRepository.findSlotByName(name);
    }

    public List<Slot> getAvailableSlots(long dentistId, String dayOff) {
        List<Long> excludedSlotIds = workingDayOffRepository.findSlotIdsByDentistAndDayOff(dentistId, dayOff);
        return slotRepository.findAvailableSlotsExcluding(excludedSlotIds);
    }

//    public List<Slot> getSlotsByDentist(long id ) {
//        var account = accountRepository.findById(id);
//        if (account.getRole() == Role.DENTIST) {
//            return slotRepository.findByAccountId(id);
//        } else {
//            throw new InvalidRoleException("The " + account.getRole() + " role is invalid");
//        }
//    }

//    public List<Slot> getSlotsByRoom(long room_id) {
//        var room = roomRepository.findById(room_id);
//        if (room.getId()==room_id) {
//            return slotRepository.findByRoomId(room_id);
//        } else {
//            throw new NotFoundException("Room not found!");
//        }
//    }

//    public List<Slot> getSlotsByDate(String date) {
//        return slotRepository.findByDate(date);
//    }

    public Slot createSlot(SlotRequest slotRequest)
    {
        Slot slot = new Slot();
        slot.setName(slotRequest.getName());
        slot.setStartTime(slotRequest.getStartTime());
        slot.setEndTime(slotRequest.getEndTime());
        slot.setMaxPatient(slotRequest.getMaxPatient());
//        slot.setDate(slotRequest.getDate());
//        slot.setStatus(Status.ACTIVE);
        try {
            return slotRepository.save(slot);
        } catch (Exception e) {
            throw new DuplicateException("Slot already exists!");
        }
    }

    public Slot updateSlot(SlotUpdateRequest slotUpdateRequest)
    {
        Slot slot = slotRepository.findById(slotUpdateRequest.getId());
        if (slot != null) {
            slot.setName(slotUpdateRequest.getName());
            slot.setStartTime(slotUpdateRequest.getStartTime());
            slot.setEndTime(slotUpdateRequest.getEndTime());
            slot.setMaxPatient(slotUpdateRequest.getMaxPatient());
//            slot.setDate(slotUpdateRequest.getDate());
//            slot.setStatus(slotUpdateRequest.getStatus());
            return slotRepository.save(slot);
        } else {
            throw new NotFoundException("Slot not found!");
        }
    }
}
