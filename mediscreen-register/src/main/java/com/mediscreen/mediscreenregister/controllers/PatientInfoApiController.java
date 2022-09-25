package com.mediscreen.mediscreenregister.controllers;

import com.mediscreen.mediscreenregister.DTO.PatientInfoRiskDTO;
import com.mediscreen.mediscreenregister.exceptions.FullNameException;
import com.mediscreen.mediscreenregister.models.PatientInfo;
import com.mediscreen.mediscreenregister.services.PatientInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientInfoApiController {
    @Autowired
    private PatientInfoService patientInfoService;

    private final Logger log = LogManager.getLogger(PatientInfoController.class);

    @GetMapping("/patientName/{fullName}")
    public ResponseEntity<PatientInfo> getPatientInfo(@PathVariable("fullName") final @NotBlank String fullName) throws FullNameException {

        log.info("Getting patient information with name : " + fullName);
        PatientInfo patientInfo = null;
        patientInfo = patientInfoService.getPatientInfo(fullName);
        if (patientInfo != null) {
            return new ResponseEntity<>(patientInfo, HttpStatus.OK);
        } else {
            throw new NoSuchElementException();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<PatientInfo>> getPatientInfoById(@PathVariable("id") @NotBlank Long id) {
        Optional<PatientInfo> patientInfo = patientInfoService.findById(id);
        if (patientInfo.isPresent()) {
            return new ResponseEntity<>(patientInfo, HttpStatus.OK);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Creates and save a patient in BDD
     *
     * @return ResponseEntity http status:201
     */

    @PostMapping("/add")
    public ResponseEntity<Void> createPatientInfo(@RequestBody PatientInfo patientInfo) {
        log.info("Creating patient: " + patientInfo.toString());
        try {
            PatientInfo patientAdded = patientInfoService.savePatientInfo(patientInfo);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(patientAdded.getId()).toUri();
            return ResponseEntity.created(location).build();

        } catch (Exception e) {
            log.error("Error on patient creation");
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/{fullName}")
    public ResponseEntity<PatientInfo> updatepatientInfo(@PathVariable("fullName") final @NotBlank String fullName, @RequestBody PatientInfo patientInfo) throws NoSuchElementException, FullNameException {

        ResponseEntity<PatientInfo> patientInfoUpdated = this.getPatientInfo(fullName);

        if (patientInfoUpdated.getBody() != null) {
            if (patientInfo.getFirstName() != null)
                patientInfoUpdated.getBody().setFirstName(patientInfo.getFirstName());
            if (patientInfo.getLastName() != null) patientInfoUpdated.getBody().setLastName(patientInfo.getLastName());
            if (patientInfo.getDateOfBirth() != null)
                patientInfoUpdated.getBody().setDateOfBirth(patientInfo.getDateOfBirth());
            if (patientInfo.getGender() != null) patientInfoUpdated.getBody().setGender(patientInfo.getGender());
            if (patientInfo.getAddress() != null) patientInfoUpdated.getBody().setAddress(patientInfo.getAddress());
            if (patientInfo.getPhone() != null) patientInfoUpdated.getBody().setPhone(patientInfo.getPhone());

            log.info("Updating patientInfo information" + patientInfoUpdated.toString());
            PatientInfo patientInfoToUpdate = patientInfoService.savePatientInfo(patientInfoUpdated.getBody());

            return new ResponseEntity<>(patientInfoToUpdate, HttpStatus.OK);
        } else {
            throw new NoSuchElementException();
        }
    }
    @GetMapping("/riskInfo/{id}")
    public ResponseEntity<PatientInfoRiskDTO> getPatientInfoForRisk(@PathVariable("id") Long id) {
        Optional<PatientInfo> patientInfo = patientInfoService.findById(id);
        if (patientInfo.isPresent()) {
            PatientInfoRiskDTO patientInfoRiskDTO = patientInfoToDTO(patientInfo.get());
            return new ResponseEntity<>(patientInfoRiskDTO, HttpStatus.OK);
        } else {
            throw new NoSuchElementException();
        }
    }

    private PatientInfoRiskDTO patientInfoToDTO(PatientInfo patientInfo) {
        PatientInfoRiskDTO patientInfoRiskDTO = new PatientInfoRiskDTO();
        patientInfoRiskDTO.setDateOfBirth(patientInfo.getDateOfBirth());
        patientInfoRiskDTO.setGender(patientInfo.getGender());
        patientInfoRiskDTO.setFirstName(patientInfo.getFirstName());
        patientInfoRiskDTO.setLastName(patientInfo.getLastName());
        return patientInfoRiskDTO;
    }

}
