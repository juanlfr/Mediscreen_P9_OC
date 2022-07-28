package com.mediscreen.mediscreenrisk.services;

import com.mediscreen.mediscreenrisk.model.PatientHistoryBean;
import com.mediscreen.mediscreenrisk.model.PatientInfoBean;
import com.mediscreen.mediscreenrisk.proxies.PatientHistoryProxy;
import com.mediscreen.mediscreenrisk.proxies.PatientInfoProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class RiskServiceImpl implements RiskService {

    @Autowired
    private PatientInfoProxy patientInfoProxy;

    @Autowired
    private PatientHistoryProxy patientHistoryProxy;

    @Override
    public PatientInfoBean getPatientInfoById(String patientId) {
        return patientInfoProxy.getPatientInfo(Long.parseLong(patientId));
    }

    @Override
    public List<PatientHistoryBean> getPatientHistoryById(String patientId) {
        return patientHistoryProxy.getPatientHistoryNotes(patientId);
    }

    @Override
    public String diabetesAssess(PatientInfoBean patientInfoBean, List<PatientHistoryBean> patientHistoryBeans) {
        int patientAge = calculateAge(patientInfoBean.getDateOfBirth());
        int triggerWordCount = findTriggersWords(patientHistoryBeans);
        return null;
    }

    private int findTriggersWords(List<PatientHistoryBean> patientHistoryBeans) {

        for (PatientHistoryBean history : patientHistoryBeans) {
            String[] wordsArray = history.getNote().toLowerCase().split(" ");
        }
        return 0;
    }

    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
