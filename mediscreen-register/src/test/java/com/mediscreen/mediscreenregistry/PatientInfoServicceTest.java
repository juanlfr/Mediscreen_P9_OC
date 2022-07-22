package com.mediscreen.mediscreenregistry;

import com.mediscreen.mediscreenregistry.models.PatientInfo;

import com.mediscreen.mediscreenregistry.services.PatientInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.LocalDate;
import java.util.List;

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
}
