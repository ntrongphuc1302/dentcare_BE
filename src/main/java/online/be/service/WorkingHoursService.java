package online.be.service;

import online.be.entity.Account;
import online.be.entity.WorkingHours;
import online.be.exception.DuplicateException;
import online.be.exception.NotFoundException;
import online.be.model.request.WorkingHoursRequest;
import online.be.model.request.WorkingHoursUpdateRequest;
import online.be.repository.WorkingHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkingHoursService {

    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    @Autowired
    AuthenticationService authenticationService;

    public List<WorkingHours> getAllWorkingHours() {
        return workingHoursRepository.findAll();
    }

    public WorkingHours createWorkingHours(WorkingHoursRequest workingHoursRequest) {
        WorkingHours workingHours = new WorkingHours();
        Account account = authenticationService.getCurrentAccount();
        workingHours.setDate(workingHoursRequest.getDate());
        workingHours.setStartTime(workingHoursRequest.getStartTime());
        workingHours.setEndTime(workingHoursRequest.getEndTime());
        workingHours.setStatus(workingHoursRequest.getStatus());
        workingHours.setAccount(account);
        try {
            return workingHoursRepository.save(workingHours);
        } catch (Exception e) {
            throw new DuplicateException("Working Hours already exists!");
        }
    }

    public WorkingHours updateWorkingHours(WorkingHoursUpdateRequest workingHoursUpdateRequest) {
        Optional<WorkingHours> workingHours = workingHoursRepository.findById(workingHoursUpdateRequest.getId());
        if (workingHours.isPresent()) {
            workingHours.get().setDate(workingHoursUpdateRequest.getDate());
            workingHours.get().setStartTime(workingHoursUpdateRequest.getStartTime());
            workingHours.get().setEndTime(workingHoursUpdateRequest.getEndTime());
            workingHours.get().setStatus(workingHoursUpdateRequest.getStatus());
            return workingHoursRepository.save(workingHours.get());
        } else {
            throw new NotFoundException("Working Hours not found!");
        }
    }

    public void deleteWorkingHours(long id) {
        Optional<WorkingHours> workingHours = workingHoursRepository.findById(id);
        if (workingHours.isPresent()) {
            workingHoursRepository.delete(workingHours.get());
        } else {
            throw new NotFoundException("Working Hours not found!");
        }
    }


}
