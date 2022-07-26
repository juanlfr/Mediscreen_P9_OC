package com.mediscreen.mediscreenregister.services;

import com.mediscreen.mediscreenregister.exceptions.FullNameException;
import com.mediscreen.mediscreenregister.models.NoteBean;
import com.mediscreen.mediscreenregister.models.PatientIdBean;
import com.mediscreen.mediscreenregister.models.PatientInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PatientInfoService {
    PatientInfo getPatientInfo(String fullName) throws FullNameException;

    PatientInfo savePatientInfo(PatientInfo patientInfo);

    List<PatientInfo> findAll();

    Optional<PatientInfo> findById(Long id);

    List<NoteBean> getAllPatientHistoryNotes(String id);

    ResponseEntity<Void> createPatientHistoryNote(NoteBean noteBean);


    String generateRiskReport(PatientIdBean patientId);
}

