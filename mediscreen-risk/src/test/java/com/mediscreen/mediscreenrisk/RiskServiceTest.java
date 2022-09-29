package com.mediscreen.mediscreenrisk;

import com.mediscreen.mediscreenrisk.enums.RiskLevel;
import com.mediscreen.mediscreenrisk.model.PatientHistoryBean;
import com.mediscreen.mediscreenrisk.model.PatientInfoBean;
import com.mediscreen.mediscreenrisk.services.RiskServiceImpl;
import com.mediscreen.mediscreenrisk.utils.RiskUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assumptions.assumeTrue;


@SpringBootTest
public class RiskServiceTest {

    private  List<PatientHistoryBean> patientHistoryBeans;

    private PatientHistoryBean patientHistory1TriggerWord;

    private PatientHistoryBean patientHistory2TriggerWord;

    private PatientHistoryBean patientHistory3TriggerWord;
    private PatientHistoryBean patientHistory4TriggerWord;
    private PatientHistoryBean patientHistory5TriggerWord;
    private PatientHistoryBean patientHistory6TriggerWord;
    private PatientHistoryBean patientHistory7TriggerWord;
    private PatientHistoryBean patientHistory8TriggerWord;
    @Autowired
    RiskServiceImpl riskService;

    @BeforeEach
    public void setUpTests() {
        patientHistoryBeans = new ArrayList<>();
        patientHistory1TriggerWord = new PatientHistoryBean("Tests de laboratoire indiquant une microalbumine élevée");
        patientHistory2TriggerWord = new PatientHistoryBean("Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite. Taille incluse dans la fourchette concernée");
        patientHistory3TriggerWord = new PatientHistoryBean("Le patient déclare avoir eu plusieurs épisodes de vertige hemoglobine a1c poids");
        patientHistory4TriggerWord = new PatientHistoryBean("Le patient déclare avoir eu plusieurs épisodes de vertige fumeur anormal rechute");
        patientHistory5TriggerWord = new PatientHistoryBean("Le patient déclare avoir eu plusieurs épisodes de vertige cholésterol reaction anticorps taille");
        patientHistory6TriggerWord = new PatientHistoryBean("Le patient déclare avoir eu plusieurs épisodes de vertige cholesterol reaction anticorps taille poids");
        patientHistory7TriggerWord = new PatientHistoryBean("Le patient déclare avoir eu plusieurs épisodes de vertige cholesterol reaction anticorps taille poids microalbumine");
        patientHistory8TriggerWord = new PatientHistoryBean("Le patient déclare avoir eu plusieurs épisodes de vertige cholesterol reaction anticorps taille poids microalbumine anormal");


    }

    @Test
    public void getPatientInfoTest() {
        assumeTrue(isMicroserviceRegisterRunning());
        PatientInfoBean patientInfoBean = riskService.getPatientInfoById("1");
        System.out.println(patientInfoBean.toString());
        Assertions.assertNotNull(patientInfoBean);
    }
    @Test
    public void getPatientHistoryNoteTest() {
        assumeTrue(isMicroserviceNoteRunning());
        List<PatientHistoryBean> patientHistoryBeans = riskService.getPatientHistoryById("2");
        patientHistoryBeans.forEach(note -> System.out.println(patientHistoryBeans));
        Assertions.assertNotNull(patientHistoryBeans);
    }
    @Test
    public void findTriggersWordsTest() {
        List<PatientHistoryBean> patientHistoryBeans = new ArrayList<>();
        PatientHistoryBean patientHistory1TriggerWord = new PatientHistoryBean("Tests de laboratoire indiquant une microalbumine élevée");
        PatientHistoryBean patientHistory2TriggerWord = new PatientHistoryBean("Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite. Taille incluse dans la fourchette concernée");
        patientHistoryBeans.add(patientHistory1TriggerWord);
        patientHistoryBeans.add(patientHistory2TriggerWord);
        int calculatedWordCount = riskService.findTriggersWords(patientHistoryBeans);
        Assertions.assertEquals(3, calculatedWordCount);
        patientHistoryBeans.clear();
    }
    @Test
    public void diabetesAssess() {
        PatientInfoBean patientRiskNone = new PatientInfoBean("Pippa", "Rees", LocalDate.parse("1952-09-27"),"F");
        PatientInfoBean patientRiskBorderLine = new PatientInfoBean("Lucas", "Ferguson", LocalDate.parse("1968-06-22"),"M");
        PatientInfoBean patientDangerCas1Tw3 = new PatientInfoBean("Pedro", "Perez", LocalDate.parse("2000-01-01"),"M");
        PatientInfoBean patientDangerCas2Tw4 = new PatientInfoBean("Marie", "Lafolle", LocalDate.parse("2000-01-01"),"F");
        PatientInfoBean patientDangerCas3Tw6 = new PatientInfoBean("Edward", "Arnold", LocalDate.parse("1952-11-11"),"M");
        PatientInfoBean patientEarlyOnsetCas1Tw5 = new PatientInfoBean("Juan", "Rockas", LocalDate.parse("2000-01-01"),"M");
        PatientInfoBean patientEarlyOnsetCas2Tw7 = new PatientInfoBean("Jullietta", "Zidane", LocalDate.parse("2000-01-01"),"F");
        PatientInfoBean patientEarlyOnsetCas3Tw8 = new PatientInfoBean("Anthony", "Sharp", LocalDate.parse("1946-11-26"),"M");

        //NONE
        String riskReportNone = riskService.diabetesAssess(patientRiskNone, patientHistoryBeans);
        Assertions.assertEquals("Patient: Pippa Rees (age " + RiskUtils.calculateAge(patientRiskNone.getDateOfBirth()) + ") diabetes assessment is: None", riskReportNone);
        //BORDERLINE
        patientHistoryBeans.add(patientHistory2TriggerWord);
        String riskReportBorderline = riskService.diabetesAssess(patientRiskBorderLine, patientHistoryBeans );
        patientHistoryBeans.clear();
        Assertions.assertEquals("Patient: Lucas Ferguson (age "+ RiskUtils.calculateAge(patientRiskBorderLine.getDateOfBirth()) +") diabetes assessment is: Borderline", riskReportBorderline);
        //DANGER Cas 1
        patientHistoryBeans.add(patientHistory3TriggerWord);
        String riskReportDanger = riskService.diabetesAssess(patientDangerCas1Tw3, patientHistoryBeans);
        patientHistoryBeans.clear();
        Assertions.assertEquals("Patient: Pedro Perez (age " + RiskUtils.calculateAge(patientDangerCas1Tw3.getDateOfBirth()) + ") diabetes assessment is: Danger", riskReportDanger);
        //EARLY ONSET Cas 1
        patientHistoryBeans.add(patientHistory5TriggerWord);
        String riskReportEarlyOnset1 = riskService.diabetesAssess(patientEarlyOnsetCas1Tw5, patientHistoryBeans);
        patientHistoryBeans.clear();
        Assertions.assertEquals("Patient: Juan Rockas (age " + RiskUtils.calculateAge(patientEarlyOnsetCas1Tw5.getDateOfBirth()) +") diabetes assessment is: Early onset", riskReportEarlyOnset1);

    }
    @Test
    public void calculateRiskLevelTest() {
        //NONE
        RiskLevel riskNone = riskService.calculateRisk(20, "M", 0);
        Assertions.assertEquals(RiskLevel.NONE, riskNone);
        //BORDERLINE
        RiskLevel riskBorderline = riskService.calculateRisk( 35, "M", 2);
        Assertions.assertEquals(RiskLevel.BORDERLINE, riskBorderline);
        //DANGER Cas 1
        RiskLevel riskDanger1 = riskService.calculateRisk( 22, "M", 3);
        Assertions.assertEquals(RiskLevel.DANGER, riskDanger1);
        //DANGER cas 2
        RiskLevel riskDanger2 = riskService.calculateRisk( 21, "F", 4);
        Assertions.assertEquals(RiskLevel.DANGER, riskDanger2);
        //DANGER cas 3
        RiskLevel riskDanger3 = riskService.calculateRisk( 40, "M", 6);
        Assertions.assertEquals(RiskLevel.DANGER, riskDanger3);
        //EARLY ONSET Cas 1
        RiskLevel riskEarlyOnset1 = riskService.calculateRisk( 20, "M", 5);
        Assertions.assertEquals(RiskLevel.EARLY_ONSET, riskEarlyOnset1);
        //EARLY ONSET Cas 2
        RiskLevel riskEarlyOnset2 = riskService.calculateRisk( 27, "F", 7);
        Assertions.assertEquals(RiskLevel.EARLY_ONSET, riskEarlyOnset2);
        //EARLY ONSET Cas 3
        RiskLevel riskEarlyOnset3 = riskService.calculateRisk( 40, "M", 8);
        Assertions.assertEquals(RiskLevel.EARLY_ONSET, riskEarlyOnset3);

    }
    private boolean isMicroserviceRegisterRunning() {
        boolean isUpAndPatientIdExist = false;
        try {
            if (riskService.getPatientInfoById("1") != null) {
                isUpAndPatientIdExist = true;
            }
        }catch (Exception e) {
            //microservice down
        }
        return isUpAndPatientIdExist;
    }
    private boolean isMicroserviceNoteRunning() {
        boolean isUpAndPatientIdExist = false;
        try {
            if(riskService.getPatientHistoryById("2").size() > 0) {
                isUpAndPatientIdExist = true;
            }
        }catch (Exception e) {
            //microservice down
        }
        return isUpAndPatientIdExist;
    }



}
