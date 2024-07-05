package online.be.service;

import online.be.entity.Account;
import online.be.entity.Qualification;
import online.be.enums.QualificationEnum;
import online.be.enums.Role;
import online.be.enums.Status;
import online.be.exception.InvalidRoleException;
import online.be.exception.NotFoundException;
import online.be.model.request.QualificationRequest;
import online.be.repository.AccountRepository;
import online.be.repository.QualificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualificationService {

    @Autowired
    QualificationRepository qualificationRepository;

    @Autowired
    AccountRepository accountRepository;

    public List<Qualification> getAllQualificationByManager() {
        return qualificationRepository.findAll();
    }

    public List<Qualification> getAllQualification() {
        return qualificationRepository.findAllByQualificationEnum(QualificationEnum.APPROVED);
    }

    public Qualification getQualificationById(long id) {
        Qualification qualification = qualificationRepository.findById(id);
        if (qualification != null) {
            return qualificationRepository.findById(id);
        } else {
            throw new NotFoundException("Cannot found the " + id + " qualification id");
        }
    }

    public List<Qualification> getQualificationByDentistId(long id) {
        return qualificationRepository.findByAccountId(id);
    }


    public Qualification createQualification(QualificationRequest qualificationRequest) {
        Qualification qualification = new Qualification();
        Account account = accountRepository.findById(qualificationRequest.getDentistId());
        qualification.setName(qualificationRequest.getName());
        qualification.setInstitution(qualificationRequest.getInstitution());
        qualification.setYearObtained(qualificationRequest.getYearObtained());
        qualification.setDescription(qualificationRequest.getDescription());
        if (account.getRole() != Role.DENTIST) {
            throw new InvalidRoleException("The "  + account.getRole() + " is invalid");
        }
        qualification.setAccount(account);
        qualification.setQualificationEnum(QualificationEnum.APPROVED);
        return qualificationRepository.save(qualification);
    }

    public Qualification updateQualification(QualificationRequest qualificationRequest) {
        Qualification qualification = qualificationRepository.findById(qualificationRequest.getId());
        if (qualification != null) {
            qualification.setName(qualificationRequest.getName());
            qualification.setInstitution(qualificationRequest.getInstitution());
            qualification.setYearObtained(qualificationRequest.getYearObtained());
            qualification.setDescription(qualificationRequest.getDescription());
            qualification.setQualificationEnum(qualificationRequest.getQualificationEnum());
            return qualificationRepository.save(qualification);
        } else {
            throw new NotFoundException("Qualification id " + qualificationRequest.getId() + " has not been found");
        }
    }

    public void deleteQualification(QualificationRequest qualificationRequest) {
        Qualification qualification = qualificationRepository.findById(qualificationRequest.getId());
        if (qualification != null) {
            qualification.setQualificationEnum(QualificationEnum.EXPIRED);
            qualificationRepository.save(qualification);
        } else {
            throw new NotFoundException("Qualification id " + qualificationRequest.getId() + " has not been found");
        }
    }
}
