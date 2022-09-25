package com.mediscreen.mediscreenregister.services;

import com.mediscreen.mediscreenregister.exceptions.FullNameException;
import com.mediscreen.mediscreenregister.models.NoteBean;
import com.mediscreen.mediscreenregister.models.PatientIdBean;
import com.mediscreen.mediscreenregister.models.PatientInfo;
import com.mediscreen.mediscreenregister.proxies.MediscreenNotesProxy;
import com.mediscreen.mediscreenregister.proxies.MediscreenRiskProxy;
import com.mediscreen.mediscreenregister.repositories.PatientInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientInfoServiceImpl implements PatientInfoService {

    @Autowired
    private PatientInfoRepository patientInfoRepository;

    @Autowired
    private MediscreenNotesProxy mediscreenNotesProxy;

    @Autowired
    private MediscreenRiskProxy mediscreenRiskProxy;

    public PatientInfo getPatientInfo(String fullName) throws FullNameException {
        String[] name = fullName.trim().split("\\s+");
        if (name.length > 1) {
            return patientInfoRepository.findByFirstNameAndLastName(name[0], name[1]);
        } else {
            throw new FullNameException("At least a two words name is required");
        }

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

    @Override
    public List<NoteBean> getAllPatientHistoryNotes(String id) {
        return mediscreenNotesProxy.findAllById(id);
    }
    @Override
    public ResponseEntity<Void> createPatientHistoryNote(NoteBean noteBean) {
        return mediscreenNotesProxy.createNote(noteBean);
    }


    @Override
    public String generateRiskReport(PatientIdBean patientId) {
        return mediscreenRiskProxy.generateReport(patientId).getBody();
    }
}
