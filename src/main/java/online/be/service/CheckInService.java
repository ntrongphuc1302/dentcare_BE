package online.be.service;

import online.be.entity.Account;
import online.be.entity.AppointmentPatient;
import online.be.entity.CheckIn;
import online.be.exception.DuplicateException;
import online.be.exception.NotFoundException;
import online.be.model.request.CheckInRequest;
import online.be.model.request.CheckInUpdateRequest;
import online.be.repository.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    AuthenticationService authenticationService;

    public List<CheckIn> getAllCheckIns() {
        return checkInRepository.findAll();
    }

    public CheckIn findById(long id) {
        return checkInRepository.findById(id);
    }

    public CheckIn createCheckIn(CheckInRequest checkInRequest)
    {
        Account account = authenticationService.getCurrentAccount();
        CheckIn checkIn = new CheckIn();
        AppointmentPatient appointmentPatient = new AppointmentPatient();
        checkIn.setCheckInTime(checkInRequest.getCheckInTime());
        checkIn.setCheckIn(checkInRequest.isCheckIn());
        checkIn.setCancel(checkInRequest.isCancel());
        checkIn.setAppointmentPatient(appointmentPatient);
        checkIn.setAccount(account);
        return checkInRepository.save(checkIn);
    }

    public CheckIn updateCheckIn(CheckInUpdateRequest checkInUpdateRequest)
    {
        CheckIn checkIn = checkInRepository.findById(checkInUpdateRequest.getId());
        if (checkIn != null) {
            checkIn.setCheckInTime(checkInUpdateRequest.getCheckInTime());
            checkIn.setCheckIn(checkInUpdateRequest.isCheckIn());
            checkIn.setCancel(checkInUpdateRequest.isCancel());
            return checkInRepository.save(checkIn);
        } else {
            throw new NotFoundException("CheckIn not found!");
        }

    }
}
