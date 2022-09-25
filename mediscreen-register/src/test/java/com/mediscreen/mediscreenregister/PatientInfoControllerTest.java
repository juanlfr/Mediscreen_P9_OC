package com.mediscreen.mediscreenregister;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediscreen.mediscreenregister.controllers.PatientInfoApiController;
import com.mediscreen.mediscreenregister.models.PatientInfo;
import com.mediscreen.mediscreenregister.services.PatientInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientInfoApiController.class)
public class PatientInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientInfoService patientInfoService;

    private PatientInfo patientInfo;


    @BeforeEach
    private void setUpPatientInfo() {
        patientInfo = new PatientInfo();
        patientInfo.setFirstName("Patient");
        patientInfo.setLastName("Dupont");
        patientInfo.setDateOfBirth(LocalDate.parse("1995-12-25"));
        patientInfo.setGender("F");
        patientInfo.setAddress("7 Rue de Rennes");
        patientInfo.setPhone("12345678");
    }
    @Test
    public void getPatientInfoTest() throws Exception {
        Mockito.when(patientInfoService.getPatientInfo(Mockito.any(String.class))).thenReturn(patientInfo);
        this.mockMvc.perform(get("/api/v1/patient/patientName/Patient Dupont"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Patient")));

    }

    @Test
    public void getPatientInfoNotFoundTest() throws Exception {
        Mockito.when(patientInfoService.getPatientInfo(Mockito.any(String.class))).thenReturn(null);
        this.mockMvc.perform(get("/api/v1/patient/patientName/Patient Unknown"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException));

    }

    @Test
    public void getPatientInfoByIdTest() throws Exception {
        Mockito.when(patientInfoService.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(patientInfo));
        this.mockMvc.perform(get("/api/v1/patient/id/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Patient")));
    }
    @Test
    public void createPatientInfoTest() throws Exception {

        when(patientInfoService.savePatientInfo(Mockito.any(PatientInfo.class))).thenReturn(patientInfo);

        this.mockMvc.perform(post("/api/v1/patient/add")
                        .content(toJsonString(patientInfo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    public void updatePatientInfoTest() throws Exception {

        PatientInfo patientInfoToUpdate = new PatientInfo();
        patientInfoToUpdate.setPhone("6666666666");

        when(patientInfoService.getPatientInfo(Mockito.anyString())).thenReturn(patientInfo);
        when(patientInfoService.savePatientInfo(Mockito.any(PatientInfo.class))).thenReturn(patientInfo);


        this.mockMvc.perform(put("/api/v1/patient/update/Patient Dupont")
                        .content(toJsonString(patientInfoToUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(patientInfo.getPhone()).isEqualTo("6666666666");
    }
    private String toJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule((new JavaTimeModule()));
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
