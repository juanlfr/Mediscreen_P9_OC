package com.mediscreen.mediscreenrisk.controllers;

import com.mediscreen.mediscreenrisk.model.PatientHistoryBean;
import com.mediscreen.mediscreenrisk.model.PatientInfoBean;
import com.mediscreen.mediscreenrisk.services.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RiskController {

    @Autowired
    RiskService riskService;

    @PostMapping("/assess/id")
    public ResponseEntity<String> assessDiabetesRisk(@RequestBody String patientId) {
        PatientInfoBean patientInfoBean = riskService.getPatientInfoById(patientId);
        List<PatientHistoryBean> patientHistoryBeans = riskService.getPatientHistoryById(patientId);
        String deabetesAssess = riskService.diabetesAssess(patientInfoBean, patientHistoryBeans);
        return new ResponseEntity<>(deabetesAssess, HttpStatus.OK);
    }

}
