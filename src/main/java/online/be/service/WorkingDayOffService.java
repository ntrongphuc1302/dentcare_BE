package online.be.service;

import online.be.entity.Account;
import online.be.entity.Slot;
import online.be.entity.WorkingDayOff;
import online.be.enums.Role;
import online.be.exception.InvalidRoleException;
import online.be.exception.NotFoundException;
import online.be.model.request.DayOffRequest;
import online.be.repository.AccountRepository;
import online.be.repository.SlotRepository;
import online.be.repository.WorkingDayOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkingDayOffService {

    @Autowired
    WorkingDayOffRepository workingDayOffRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SlotRepository slotRepository;

    public List<WorkingDayOff> getAllWorkingDayOffs() {
        return workingDayOffRepository.findAll();
    }

    public List<WorkingDayOff> getByDentistAndDayOff(long id, LocalDate date) {
        Account account = accountRepository.findById(id);
        if (account.getRole() != Role.DENTIST) {
            throw new InvalidRoleException("The role " + account.getRole() + " is invalid.");
        }

        return workingDayOffRepository.findByAccountIdAndDayOff(id, date);
    }

    public WorkingDayOff createWorkingDayOff(DayOffRequest dayOffRequest) {
        WorkingDayOff workingDayOff = new WorkingDayOff();
        Account account = accountRepository.findById(dayOffRequest.getDentistId());
        Slot slot = slotRepository.findById(dayOffRequest.getSlotId());

        workingDayOff.setDayOff(dayOffRequest.getDayOff());
        // Kiểm tra xem account và slot có tồn tại không
        if (account == null) {
            throw new NotFoundException("Account with id " + dayOffRequest.getDentistId() + " not found.");
        }
        if (slot == null) {
            throw new NotFoundException("Slot with id " + dayOffRequest.getSlotId() + " not found.");
        }

        // Kiểm tra xem account có phải là DENTIST không
        if (account.getRole() != Role.DENTIST) {
            throw new InvalidRoleException("Account with id " + dayOffRequest.getDentistId() + " is not a dentist.");
        }

        workingDayOff.setAccount(account);
        workingDayOff.setSlot(slot);

        return workingDayOffRepository.save(workingDayOff);
    }
}
