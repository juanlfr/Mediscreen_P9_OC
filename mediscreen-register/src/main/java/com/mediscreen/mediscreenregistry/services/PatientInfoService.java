package com.mediscreen.mediscreenregistry.services;

import com.mediscreen.mediscreenregistry.models.PatientInfo;

import java.util.List;
import java.util.Optional;

public interface PatientInfoService {
    PatientInfo getPatientInfo(String fullName);

    PatientInfo savePatientInfo(PatientInfo patientInfo);

    List<PatientInfo> findAll();

    Optional<PatientInfo> findById(Long id);
}

