package com.mediscreen.mediscreenrisk.controllers;

import com.mediscreen.mediscreenrisk.model.PatientHistoryBean;
import com.mediscreen.mediscreenrisk.model.PatientIdDTO;
import com.mediscreen.mediscreenrisk.model.PatientInfoBean;
import com.mediscreen.mediscreenrisk.services.RiskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RiskController {

    private final Logger log = LogManager.getLogger(RiskController.class);

    @Autowired
    RiskService riskService;

    @PostMapping("/assess/id")
    public ResponseEntity<String> assessDiabetesRisk(@RequestBody PatientIdDTO patientId) {
        PatientInfoBean patientInfoBean = riskService.getPatientInfoById(patientId.getPatId());
        List<PatientHistoryBean> patientHistoryBeans = riskService.getPatientHistoryById(patientId.getPatId());
        if (patientInfoBean == null || patientHistoryBeans.isEmpty()) {
            log.error("Patient info or history not found");
            return new ResponseEntity<>("Patient info or history not found", HttpStatus.NOT_FOUND);
        } else {
            log.info("Risk Assessment for patient: " + patientInfoBean.getLastName());
            String deabetesAssess = riskService.diabetesAssess(patientInfoBean, patientHistoryBeans);
            return new ResponseEntity<>(deabetesAssess, HttpStatus.OK);
        }
    }

}
