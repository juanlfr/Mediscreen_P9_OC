package com.mediscreen.mediscreenrisk;

import com.mediscreen.mediscreenrisk.model.PatientHistoryBean;
import com.mediscreen.mediscreenrisk.model.PatientInfoBean;
import com.mediscreen.mediscreenrisk.services.RiskServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class RiskServiceTest {

    @Autowired
    RiskServiceImpl riskService;

    @Test
    public void getPatientInfoTest() {
        PatientInfoBean patientInfoBean = riskService.getPatientInfoById("1");
        System.out.println(patientInfoBean.toString());
        Assertions.assertNotNull(patientInfoBean);
    }
    @Test
    public void getPatientHistoryNoteTest() {
        List<PatientHistoryBean> patientHistoryBeans = riskService.getPatientHistoryById("2");
        patientHistoryBeans.forEach(note -> System.out.println(patientHistoryBeans));
        Assertions.assertNotNull(patientHistoryBeans);
    }
}
