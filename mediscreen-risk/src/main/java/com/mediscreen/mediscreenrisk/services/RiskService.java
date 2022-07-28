package com.mediscreen.mediscreenrisk.services;

import com.mediscreen.mediscreenrisk.model.PatientHistoryBean;
import com.mediscreen.mediscreenrisk.model.PatientInfoBean;

import java.util.List;

public interface RiskService {
    PatientInfoBean getPatientInfoById(String patientId);

    List<PatientHistoryBean> getPatientHistoryById(String patientId);

    String diabetesAssess(PatientInfoBean patientInfoBean, List<PatientHistoryBean> patientHistoryBeans);
}
