package com.mediscreen.mediscreenregister.controllers;

import com.mediscreen.mediscreenregister.DTO.PatientInfoRiskDTO;
import com.mediscreen.mediscreenregister.models.NoteBean;
import com.mediscreen.mediscreenregister.services.PatientInfoService;
import com.mediscreen.mediscreenregister.models.PatientInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientInfoController {

    @Autowired
    private PatientInfoService patientInfoService;

    private final Logger log = LogManager.getLogger(PatientInfoController.class);

    //Methods below for Thymeleaf IHM

    @GetMapping("/patientInfo")
    public String getPatientsInfo(Model model) {
        model.addAttribute("patientsInfoList", patientInfoService.findAll());
        return "list";
    }
    @GetMapping("/add")
    public String addPatientInfoForm(PatientInfo patientInfo, Model model) {
        model.addAttribute("patientInfo", new PatientInfo());
        return "add";
    }

    @PostMapping("/form")
    public String formCreate(@ModelAttribute("patientInfo") @Valid PatientInfo patientInfo, BindingResult result) {

        log.info("validate create patient method");
        if (result.hasErrors()) {
            return "add";
        }
        try {
            patientInfoService.savePatientInfo(patientInfo);
            log.info("Creating patientInfo" + patientInfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Creating patientInfo");
        }
        return "redirect:/patient/patientInfo";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {

        PatientInfo patientInfo = getPatientInfoById(id).getBody().get();
        log.info("Finding patientInfo");
        model.addAttribute("patientInfo", patientInfo);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updatePatientInfoForm(@PathVariable("id") Long id, @Valid PatientInfo patientInfo,
                                        BindingResult result) {

        if (result.hasErrors()) {
            return "patient/update";
        }
        try {
            Optional<PatientInfo> patientInfoUpdated = getPatientInfoById(id).getBody();

            if (patientInfoUpdated.isPresent()) {
                patientInfoUpdated.get().setFirstName(patientInfo.getFirstName());
                patientInfoUpdated.get().setLastName(patientInfo.getLastName());
                patientInfoUpdated.get().setDateOfBirth(patientInfo.getDateOfBirth());
                patientInfoUpdated.get().setGender(patientInfo.getGender());
                patientInfoUpdated.get().setGender(patientInfo.getGender());
                patientInfoUpdated.get().setAddress(patientInfo.getAddress());
                patientInfoUpdated.get().setPhone(patientInfo.getPhone());
                patientInfoService.savePatientInfo(patientInfoUpdated.get());
                log.info("Updating patienttInfo" + patientInfoUpdated.get());
            } else {
                log.error("Patient id not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while updating patient info");
        }
        return "redirect:/patient/patientInfo";
    }
    @GetMapping("/patHistory/{id}")
    public String showPatientHistory(@PathVariable("id") String id, Model model) {
        List<NoteBean> notesBean = patientInfoService.getAllPatientHistoryNotes(id);
        model.addAttribute("notes", notesBean);
        return "patientHistory";
    }
    @GetMapping("/patHistory/add/{patientId}")
    public String addPatientHistoryForm(@PathVariable("patientId") String patientId, Model model) {
        NoteBean noteBean = new NoteBean();
        noteBean.setPatientId(patientId);
        model.addAttribute("note", noteBean);
        return "addHistory";
    }
    @PostMapping("/patHistory/form")
    public String formCreateHistoryNote(@ModelAttribute("note") @Valid NoteBean noteBean, BindingResult result) {

        log.info("validate create patient method");
        if (result.hasErrors()) {
            return "addHistory";
        }
        try {
            noteBean.setCreationDate(LocalDateTime.now());
            patientInfoService.createPatientHistoryNote(noteBean);
            log.info("Creating note" + noteBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Creating patient note");
        }
        return "redirect:/patient/patientInfo";
    }
    //Call to report microservice
    @GetMapping("/generateRiskReport/{patientId}")
    public String riskReport(@PathVariable String patientId, Model model){
        model.addAttribute("riskReport", patientInfoService.generateRiskReport(patientId));
        return "riskReport";
    }



    //API enpoints

    @GetMapping("/api/{fullName}")
    public ResponseEntity<PatientInfo> getPatientInfo(@PathVariable("fullName") final @NotBlank String fullName) {

        log.info("Getting patient information with name : " + fullName);
        PatientInfo patientInfo = patientInfoService.getPatientInfo(fullName);
        if (patientInfo != null) {
            return new ResponseEntity<>(patientInfo, HttpStatus.OK);
        }
        else {
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
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(patientAdded.getId())
                    .toUri();
            return ResponseEntity.created(location).build();

        } catch (Exception e) {
            log.error("Error on patient creation");
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/id/{id}")
    public ResponseEntity<Optional<PatientInfo>> getPatientInfoById(@PathVariable("id") Long id) {
        Optional<PatientInfo> patientInfo = patientInfoService.findById(id);
        if (patientInfo.isPresent()) {
            return new ResponseEntity<>(patientInfo, HttpStatus.OK);
        }
        else {
            throw new NoSuchElementException();
        }
    }
    @GetMapping("/api/riskInfo/{id}")
    public ResponseEntity<PatientInfoRiskDTO> getPatientInfoForRisk(@PathVariable("id") Long id) {
        Optional<PatientInfo> patientInfo = patientInfoService.findById(id);
        if (patientInfo.isPresent()) {
            PatientInfoRiskDTO patientInfoRiskDTO = patientInfoToDTO(patientInfo.get());
            return new ResponseEntity<>(patientInfoRiskDTO, HttpStatus.OK);
        }
        else {

            throw new NoSuchElementException();
        }
    }

    private PatientInfoRiskDTO patientInfoToDTO(PatientInfo patientInfo) {
        PatientInfoRiskDTO patientInfoRiskDTO = new PatientInfoRiskDTO();
        patientInfoRiskDTO.setDateOfBirth(patientInfo.getDateOfBirth());
        patientInfoRiskDTO.setGender(patientInfo.getGender());
        return patientInfoRiskDTO;
    }

    @PutMapping("/api/{fullName}")
    public ResponseEntity<PatientInfo> updatepatientInfo(@PathVariable("fullName") final String fullName,
                                                         @RequestBody PatientInfo patientInfo)
            throws NoSuchElementException {

        ResponseEntity<PatientInfo> patientInfoUpdated = this.getPatientInfo(fullName);

        if (patientInfoUpdated.getBody() != null) {
            if (patientInfo.getFirstName() != null)
                patientInfoUpdated.getBody().setFirstName(patientInfo.getFirstName());
            if (patientInfo.getLastName() != null)
                patientInfoUpdated.getBody().setLastName(patientInfo.getLastName());
            if (patientInfo.getDateOfBirth() != null)
                patientInfoUpdated.getBody().setDateOfBirth(patientInfo.getDateOfBirth());
            if (patientInfo.getGender() != null)
                patientInfoUpdated.getBody().setGender(patientInfo.getGender());
            if (patientInfo.getAddress() != null)
                patientInfoUpdated.getBody().setAddress(patientInfo.getAddress());
            if (patientInfo.getPhone() != null)
                patientInfoUpdated.getBody().setPhone(patientInfo.getPhone());

            log.info("Updating patientInfo information" + patientInfoUpdated.toString());
            PatientInfo patientInfoToUpdate = patientInfoService.savePatientInfo(patientInfoUpdated.getBody());

            return new ResponseEntity<>(patientInfoToUpdate, HttpStatus.OK);
        } else {
            throw new NoSuchElementException();
        }
    }


}
