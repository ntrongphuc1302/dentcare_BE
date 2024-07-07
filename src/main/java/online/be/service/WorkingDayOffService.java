package online.be.service;

import online.be.entity.Account;
import online.be.entity.Slot;
import online.be.entity.WorkingDayOff;
import online.be.enums.Role;
import online.be.exception.InvalidRoleException;
import online.be.exception.NotFoundException;
import online.be.model.request.DayOffRequest;
import online.be.model.request.DayOffUpdateRequest;
import online.be.repository.AccountRepository;
import online.be.repository.SlotRepository;
import online.be.repository.WorkingDayOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public WorkingDayOff getByDentistAndDayOff(long id, LocalDate date) {
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

    public WorkingDayOff updateWorkingDayOff(DayOffUpdateRequest dayOffUpdateRequest) {
        WorkingDayOff workingDayOff = workingDayOffRepository.findById(dayOffUpdateRequest.getId());
        Account account = accountRepository.findById(dayOffUpdateRequest.getDentistId());
        Slot slot = slotRepository.findById(dayOffUpdateRequest.getSlotId());

        if (workingDayOff == null) {
            throw new NotFoundException("Working day off with id " + dayOffUpdateRequest.getId() + " not found.");
        }
        if (account == null) {
            throw new NotFoundException("Account with id " + dayOffUpdateRequest.getDentistId() + " not found.");
        }
        if (slot == null) {
            throw new NotFoundException("Slot with id " + dayOffUpdateRequest.getSlotId() + " not found.");
        }
        if (account.getRole() != Role.DENTIST) {
            throw new InvalidRoleException("Account with id " + dayOffUpdateRequest.getDentistId() + " is not a dentist.");
        }
        workingDayOff.setDayOff(dayOffUpdateRequest.getDayOff());
        workingDayOff.setAccount(account);
        workingDayOff.setSlot(slot);

        return workingDayOffRepository.save(workingDayOff);
    }

    public WorkingDayOff getWorkingDayOffById(long id) {
        try {
            return workingDayOffRepository.findById(id);
        } catch (Exception e) {
            throw new NotFoundException("Working day off with id " + id + " not found.");
        }
    }

    public List<WorkingDayOff> getWorkingDayOffByDentistId(long id) {
        //verify if the dentist exists
        Account account = accountRepository.findById(id);
        if (account == null) {
            throw new NotFoundException("Account with id " + id + " not found.");
        }
        //verify if the account is a dentist
        if (account.getRole() != Role.DENTIST) {
            throw new InvalidRoleException("Account with id " + id + " is not a dentist.");
        }
        try{
            return workingDayOffRepository.findByAccountId(id);
        } catch (Exception e) {
            throw new NotFoundException("Working day off not found!");
        }
    }

    public List<WorkingDayOff> getWorkingDayOffBySlotId(long id) {
        Slot slot = slotRepository.findById(id);
        if (slot == null) {
            throw new NotFoundException("Slot with id " + id + " not found.");
        }
        try {
            return workingDayOffRepository.findBySlotId(id);
        } catch (Exception e) {
            throw new NotFoundException("Working day off not found!");
        }
    }
}
