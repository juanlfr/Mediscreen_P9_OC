package com.mediscreen.mediscreenrisk.services;

import com.mediscreen.mediscreenrisk.enums.RiskLevel;
import com.mediscreen.mediscreenrisk.enums.TriggerWords;
import com.mediscreen.mediscreenrisk.model.PatientHistoryBean;
import com.mediscreen.mediscreenrisk.model.PatientInfoBean;
import com.mediscreen.mediscreenrisk.proxies.PatientHistoryProxy;
import com.mediscreen.mediscreenrisk.proxies.PatientInfoProxy;
import com.mediscreen.mediscreenrisk.utils.RiskUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RiskServiceImpl implements RiskService {

    private final Logger log = LogManager.getLogger(RiskServiceImpl.class);

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
        int patientAge = RiskUtils.calculateAge(patientInfoBean.getDateOfBirth());
        String patientGender = patientInfoBean.getGender().toUpperCase();
        int triggerWordCount = findTriggersWords(patientHistoryBeans);
        RiskLevel riskLevel = calculateRisk(patientAge, patientGender, triggerWordCount );
        return "Patient: " +  patientInfoBean.getFirstName() + " " + patientInfoBean.getLastName() + " (age " + patientAge + ") diabetes assessment is: " + riskLevel.label;
    }

    public RiskLevel calculateRisk(int patientAge, String patientGender, int triggerWordCount) {
        if (triggerWordCount == 0){
            return RiskLevel.NONE;
        }
        else if (triggerWordCount == 2 && patientAge > 30){
            return  RiskLevel.BORDERLINE;
        }
        else if ((patientGender.equals("M") && patientAge < 30 && triggerWordCount == 3) ||
                (patientGender.equals("F") && patientAge < 30 && triggerWordCount == 4) ||
                (patientAge > 30 && triggerWordCount == 6)) {
            return RiskLevel.DANGER;
        } else if ((patientGender.equals("M") && patientAge < 30 && triggerWordCount == 5) ||
                (patientGender.equals("F") && patientAge < 30 && triggerWordCount == 7) ||
                patientAge > 30 && triggerWordCount >= 8) {
            return RiskLevel.EARLY_ONSET;
        }
        else {
            return RiskLevel.UNDEFINED;
        }
    }

    public int findTriggersWords(List<PatientHistoryBean> patientHistoryBeans) {
        int triggerWordCount = 0;
        for (PatientHistoryBean history : patientHistoryBeans) {
            String[] splitStringArray = RiskUtils.unaccent(history.getNote().toLowerCase()).split(" ");
            List<String> splitStringArrayList = new ArrayList<>(Arrays.asList(splitStringArray));
            if (splitStringArrayList.contains("hemoglobine") && splitStringArrayList.contains("a1c")){
                splitStringArrayList.add("hemoglobine a1c");
            }
            for (String word: splitStringArrayList) {
                if (isWordOnTheTriggerList(word)){
                    triggerWordCount++;
                }
            }
        }
        return triggerWordCount;
    }


    private boolean isWordOnTheTriggerList(String word) {
        for (TriggerWords tw: TriggerWords.values()) {
            if(tw.label.equals(word)){
                return true;
            }
        }
        return false;
    }

}
