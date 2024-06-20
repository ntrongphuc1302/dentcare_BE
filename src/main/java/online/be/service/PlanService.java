package online.be.service;

import online.be.entity.MedicalRecord;
import online.be.entity.TreatmentPlan;
import online.be.enums.Status;
import online.be.exception.DuplicateException;
import online.be.exception.NotFoundException;
import online.be.model.request.PlanRequest;
import online.be.repository.MedicalRecordRepository;
import online.be.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    @Autowired
    PlanRepository planRepository;

    @Autowired
    MedicalRecordRepository recordRepository;

    public List<TreatmentPlan> getAllTreatmentPlanByManager() {
        return planRepository.findAll();
    }

    public List<TreatmentPlan> getAllTreatmentPlan() {
        return planRepository.findAllByStatus(Status.ACTIVE);
    }

    public TreatmentPlan getPlanById(long id) {
        return planRepository.findById(id);
    }

    public List<TreatmentPlan> getPlanByRecordName(String name) {
        return planRepository.findByMedicalRecordName(name);
    }

    public TreatmentPlan getPlanByName(String name) {
        return planRepository.findByName(name);
    }

    public TreatmentPlan createPlan(PlanRequest planRequest) {

        TreatmentPlan plan = new TreatmentPlan();
        MedicalRecord record = recordRepository.findById(planRequest.getMedicalId());
        plan.setName(planRequest.getName());
        plan.setFrequency(planRequest.getFrequency());
        plan.setDescription(planRequest.getDescription());
        plan.setStatus(Status.ACTIVE);
        if (record != null) {
            plan.setMedicalRecord(record);
        } else {
            throw new NotFoundException("Cannot found the Record");
        }
        return planRepository.save(plan);

    }

    public TreatmentPlan updatePlan(PlanRequest planRequest) {
        TreatmentPlan plan = planRepository.findById(planRequest.getId());
        if (plan != null) {
            plan.setName(planRequest.getName());
            plan.setFrequency(planRequest.getFrequency());
            plan.setDescription(planRequest.getDescription());
            MedicalRecord record = recordRepository.findById(planRequest.getMedicalId());
            if (record != null) {
                plan.setMedicalRecord(record);
            } else {
                throw new NotFoundException("Cannot found the Record");
            }
            return planRepository.save(plan);
        } else {
            throw new NotFoundException(planRequest.getId() + " has not been existed");
        }
    }

    public void deletePlan(PlanRequest planRequest) {
        TreatmentPlan plan = planRepository.findById(planRequest.getId());
        if (plan != null) {
            planRequest.setStatus(Status.INACTIVE);
            planRepository.save(plan);
        } else {
            throw new NotFoundException(planRequest.getId() + " has not been existed");
        }
    }


}
