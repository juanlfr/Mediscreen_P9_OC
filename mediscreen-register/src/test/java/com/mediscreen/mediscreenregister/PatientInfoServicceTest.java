package com.mediscreen.mediscreenregister;

import com.mediscreen.mediscreenregister.models.NoteBean;
import com.mediscreen.mediscreenregister.models.PatientInfo;

import com.mediscreen.mediscreenregister.services.PatientInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PatientInfoServicceTest {
    @Autowired
    private PatientInfoServiceImpl patientInfoService;

    private PatientInfo patientInfo;
    private PatientInfo patientInfo2;


    @BeforeEach
    private void setUpPatientInfo() {
        patientInfo = new PatientInfo();
        patientInfo.setFirstName("Patient");
        patientInfo.setLastName("Dupont");
        patientInfo.setDateOfBirth(LocalDate.parse("1995-12-25"));
        patientInfo.setGender("F");
        patientInfo.setAddress("7 Rue de Rennes");
        patientInfo.setPhone("12345678");

        patientInfo2 = new PatientInfo();
        patientInfo2.setFirstName("Paciente");
        patientInfo2.setLastName("Rodriguez");
        patientInfo2.setDateOfBirth(LocalDate.parse("1995-12-25"));
        patientInfo2.setGender("F");
        patientInfo2.setAddress("7 Rue de Rennes");
        patientInfo2.setPhone("12345678");

        patientInfoService.savePatientInfo(patientInfo);
        patientInfoService.savePatientInfo(patientInfo2);
    }

    @Test
    public void getPatientInfoTest() {

        PatientInfo patientInfoRetrived = patientInfoService.getPatientInfo("Patient Dupont");
        assertEquals(patientInfoRetrived, patientInfo);
    }
    @Test
    public void findByIdTest() {

        assertEquals(patientInfo2, patientInfoService.findById(2L).get());
    }

    @Test
    public void findAllTest() {

        List<PatientInfo> patientInfos = patientInfoService.findAll();
        assertEquals(2, patientInfoService.findAll().size());
    }
    @Test
    public void getAllPatientHistoryNotesTest() {
        List<NoteBean> noteBeans = patientInfoService.getAllPatientHistoryNotes("1");
        assertNotNull(noteBeans);
    }
    @Test
    public void createHistoryNoteTest() {
        NoteBean noteBean = new NoteBean("99","99", "Doctors said that...patient is OK", LocalDateTime.now() );
        assertTrue(patientInfoService.createPatientHistoryNote(noteBean).getStatusCode().is2xxSuccessful());
    }
    @Test
    public void getRiskReportTest() {
        System.out.println(patientInfoService.generateRiskReport("1"));
        assertNotNull(patientInfoService.generateRiskReport("1"));
    }
}
