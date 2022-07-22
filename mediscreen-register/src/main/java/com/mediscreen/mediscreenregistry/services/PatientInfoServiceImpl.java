package com.mediscreen.mediscreenregistry.services;

import com.mediscreen.mediscreenregistry.models.PatientInfo;
import com.mediscreen.mediscreenregistry.repositories.PatientInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientInfoServiceImpl implements PatientInfoService {

    @Autowired
    private PatientInfoRepository patientInfoRepository;

    public PatientInfo getPatientInfo(String fullName) {
        String[] name = fullName.trim().split("\\s+");
        return patientInfoRepository.findByFirstNameAndLastName(name[0], name[1]);

    }

    public PatientInfo savePatientInfo(PatientInfo patientInfo) {
        return patientInfoRepository.save(patientInfo);
    }

    @Override
    public List<PatientInfo> findAll() {
        return patientInfoRepository.findAll();
    }

    @Override
    public Optional<PatientInfo> findById(Long id) {
        return patientInfoRepository.findById(id);
    }
}
