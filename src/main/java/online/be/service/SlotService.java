package online.be.service;

import online.be.entity.Slot;
import online.be.entity.WorkingDayOff;
import online.be.enums.Role;
import online.be.enums.Status;
import online.be.exception.DuplicateException;
import online.be.exception.InvalidRoleException;
import online.be.exception.NotFoundException;
import online.be.model.SlotIdCountDTO;
import online.be.model.request.SlotRequest;
import online.be.model.request.SlotUpdateRequest;
import online.be.model.response.SlotResponse;
import online.be.repository.AccountRepository;
import online.be.repository.RoomRepository;
import online.be.repository.SlotRepository;
import online.be.repository.WorkingDayOffRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

//    // Utility method to compare slot end time with current time
//    private boolean isSlotExpired(String endTime) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//        LocalDateTime slotEndTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(endTime, formatter));
//        return slotEndTime.isBefore(LocalDateTime.now());
//    }

    // Define a DateTimeFormatter for "h a" time format
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("h a");


    // Check if the slot date and time has already passed
    private boolean isSlotExpired(LocalDate slotDate, String slotEndTime) {
        try {
            LocalTime slotTime = LocalTime.parse(slotEndTime, TIME_FORMATTER);
            LocalDateTime slotDateTime = LocalDateTime.of(slotDate, slotTime);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiryTime = slotDateTime.minusHours(1);
            return now.isAfter(expiryTime);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            // Handle the exception as needed
            return true;
        }
    }

    public SlotResponse mapperSlot(Slot slot){
        SlotResponse slotResponse = new SlotResponse();
        slotResponse.setId(slot.getId());
        slotResponse.setName(slot.getName());
        slotResponse.setStartTime(slot.getStartTime());
        slotResponse.setEndTime(slot.getEndTime());
        slotResponse.setMaxPatient(slot.getMaxPatient());
        slotResponse.setAvailable(true);

//        slotResponse.setAvailable(!isSlotExpired(slot.getStartTime()));
        return slotResponse;
    }

    public List<SlotResponse> getAvailableSlots(long dentistId, LocalDate dayOff) {

        List<Slot> listSlot = slotRepository.findAll();

        List<SlotResponse> slotResponses =  new ArrayList<>();

        List<SlotIdCountDTO> list = new ArrayList<>();

        List<Object[]> results = slotRepository.findCountSlot(dentistId,dayOff);
        list = results.stream()
                .map(result -> new SlotIdCountDTO((Long) result[0], (Long) result[1]))
                .collect(Collectors.toList());

        List<Long> excludedSlotIds = workingDayOffRepository.
                findSlotIdsByDentistAndDayOff(dentistId, dayOff);

        List<Slot> listOffDate = slotRepository.findUnavailableSlotsExcluding(excludedSlotIds);


        for(Slot slot: listSlot){
           SlotResponse slotResponse = mapperSlot(slot);
                   if(!listOffDate.isEmpty()){
                       for(Slot offdate: listOffDate){
                           if(slot.getId() == offdate.getId()){
                               slotResponse.setAvailable(false);
                           }
                       }
                   }
                    for (SlotIdCountDTO slotIdCountDTO: list){
                        if(slot.getId() == slotIdCountDTO.getSlotId() && slotIdCountDTO.getCount() >= 3){
                            slotResponse.setAvailable(false);
                        }
                    }
                    if(dayOff.equals(LocalDate.now()) || dayOff.isBefore(LocalDate.now())){
                        slotResponse.setAvailable(!isSlotExpired(dayOff, slot.getEndTime()));
                    }
           slotResponses.add(slotResponse);
        }

        return slotResponses;
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
