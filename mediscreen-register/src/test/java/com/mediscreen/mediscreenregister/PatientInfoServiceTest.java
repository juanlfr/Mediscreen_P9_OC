package com.mediscreen.mediscreenregister;

import com.mediscreen.mediscreenregister.exceptions.FullNameException;
import com.mediscreen.mediscreenregister.models.NoteBean;
import com.mediscreen.mediscreenregister.models.PatientIdBean;
import com.mediscreen.mediscreenregister.models.PatientInfo;
import com.mediscreen.mediscreenregister.services.PatientInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PatientInfoServiceTest {

    @Autowired
    private PatientInfoServiceImpl patientInfoService;

    private PatientInfo patientInfo;
    private PatientInfo patientInfo2;

    private NoteBean noteBean;


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

        noteBean = new NoteBean("99", "Doctors said that...patient is OK", LocalDateTime.now() );

        patientInfoService.savePatientInfo(patientInfo);
        patientInfoService.savePatientInfo(patientInfo2);
    }

    @Test
    public void getPatientInfoTest() throws FullNameException {

        PatientInfo patientInfoRetrived = patientInfoService.getPatientInfo("Patient Dupont");
        assertEquals(patientInfoRetrived, patientInfo);
    }
    @Test
    public void getPatientInfoFullNameExceptionTest(){
        assertThrows(FullNameException.class, ()-> patientInfoService.getPatientInfo("Patient"));
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
        assumeTrue(isMicroserviceNoteRunning());
        List<NoteBean> noteBeans = patientInfoService.getAllPatientHistoryNotes("1");
        assertNotNull(noteBeans);
    }

    @Test
    public void createHistoryNoteTest() {
        assumeTrue(isMicroserviceNoteRunning());
        assertTrue(patientInfoService.createPatientHistoryNote(noteBean).getStatusCode().is2xxSuccessful());
    }
    @Test
    public void getRiskReportTest() {
        assumeTrue(isMicroserviceRiskRunning());
        assertNotNull(patientInfoService.generateRiskReport(new PatientIdBean("1")));
    }
    private boolean isMicroserviceNoteRunning() {
        try {
            ResponseEntity<Void> voidResponseEntity = patientInfoService.createPatientHistoryNote(noteBean);
            return voidResponseEntity.getStatusCode().is2xxSuccessful();
        }catch (Exception e) {
            return false;
        }
    }
    private boolean isMicroserviceRiskRunning() {
        PatientIdBean patientIdBean = new PatientIdBean("1");
        try {
            return !patientInfoService.generateRiskReport(patientIdBean).isEmpty();
        }catch (Exception e) {
            return false;
        }
    }
}
