package online.be.service;

import online.be.entity.AppointmentPatient;
import online.be.enums.Status;
import online.be.exception.DuplicateException;
import online.be.exception.NotFoundException;
import online.be.model.request.AppointmentRequest;
import online.be.repository.AppointmentPatientRepository;
import online.be.repository.DentistServiceRepository;
import online.be.repository.PatientRepository;
import online.be.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentPatientService {

    @Autowired
    AppointmentPatientRepository appointmentPatientRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    SlotRepository slotRepository;

    @Autowired
    DentistServiceRepository dentistServiceRepository;

    public List<AppointmentPatient> getAllAppointment() {
        return appointmentPatientRepository.findAll();
    }

    public AppointmentPatient getAppointmentById(long id) {
        return appointmentPatientRepository.findById(id);
    }

    public AppointmentPatient createAppointment(AppointmentRequest appointmentRequest) {
        AppointmentPatient appointmentPatient = appointmentPatientRepository.
                findBySlotIdAndPatientId(appointmentRequest.getSlotId(), appointmentRequest.getPatientId());

        if (appointmentPatient == null) {
            AppointmentPatient appointment = new AppointmentPatient();
            var patient = patientRepository.findById(appointmentRequest.getPatientId());
            appointment.setPatient(patient);
            var slot = slotRepository.findById(appointmentRequest.getSlotId());
            appointment.setSlot(slot);
            var dentistService = dentistServiceRepository.findById(appointmentRequest.getDentistServiceId());
            appointment.setDentistServices(dentistService);
            appointment.setStatus(Status.ACTIVE);

            return appointmentPatientRepository.save(appointment);
        } else {
            throw new DuplicateException("These id have been existed");
        }
    }

    public AppointmentPatient updateAppointment(AppointmentRequest appointmentRequest) {
//        AppointmentPatient appointmentPatient = appointmentPatientRepository.
//                findBySlotIdAndPatientId(appointmentRequest.getSlotId(), appointmentRequest.getPatientId());
        AppointmentPatient appointmentPatient = appointmentPatientRepository.findById(appointmentRequest.getId());
        if (appointmentPatient != null) {

            var patient = patientRepository.findById(appointmentRequest.getPatientId());
            appointmentPatient.setPatient(patient);
            var slot = slotRepository.findById(appointmentRequest.getSlotId());
            appointmentPatient.setSlot(slot);
            var dentistService = dentistServiceRepository.findById(appointmentRequest.getDentistServiceId());
            appointmentPatient.setDentistServices(dentistService);
//            appointmentPatient.setStatus(Status.ACTIVE);

            return appointmentPatientRepository.save(appointmentPatient);
        } else {
            throw new NotFoundException("These id have not been existed");
        }
    }

    public void deleteAppointment(AppointmentRequest appointmentRequest) {
        AppointmentPatient appointmentPatient = appointmentPatientRepository.
                findBySlotIdAndPatientId(appointmentRequest.getSlotId(), appointmentRequest.getPatientId());

        if (appointmentPatient != null) {

            appointmentPatient.setStatus(Status.INACTIVE);

            appointmentPatientRepository.save(appointmentPatient);
        } else {
            throw new NotFoundException("These id have not been existed");
        }
    }
}
