package com.mediscreen.mediscreenregister.controllers;

import com.mediscreen.mediscreenregister.models.NoteBean;
import com.mediscreen.mediscreenregister.models.PatientIdBean;
import com.mediscreen.mediscreenregister.models.PatientInfo;
import com.mediscreen.mediscreenregister.services.PatientInfoService;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class PatientInfoController {

    @Autowired
    private PatientInfoService patientInfoService;

    private final Logger log = LogManager.getLogger(PatientInfoController.class);

    //Methods below for Thymeleaf IHM
    @GetMapping(value ={"/patientInfo", "/"})
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
        return "redirect:/patientInfo";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {

        Optional<PatientInfo> patientInfo = patientInfoService.findById(id);
        if (patientInfo.isPresent()){
            log.info("Finding patientInfo");
            model.addAttribute("patientInfo", patientInfo.get());
            return "update";
        } else {
            log.info("Patient not found");
            return "PatientNotFound";
        }
    }

    @PostMapping("/update/{id}")
    public String updatePatientInfoForm(@PathVariable("id") Long id, @Valid PatientInfo patientInfo, BindingResult result) {

        if (result.hasErrors()) {
            return "patient/update";
        }
        try {
            Optional<PatientInfo> patientInfoUpdated = patientInfoService.findById(id);

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
        return "redirect:/patientInfo";
    }

    @GetMapping("/patHistory/{id}")
    public String showPatientHistory(@PathVariable("id") String id, Model model) {
        List<NoteBean> notesBean = patientInfoService.getAllPatientHistoryNotes(id);
        model.addAttribute("notes", notesBean);
        model.addAttribute("patientId", id);
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
        return "redirect:/patientInfo";
    }

    //Call to report microservice
    @GetMapping("/generateRiskReport/{patientId}")
    public String riskReport(@PathVariable String patientId, Model model) {

        try {
            PatientIdBean patientIdBean = new PatientIdBean(patientId);
            String riskReport = patientInfoService.generateRiskReport(patientIdBean);
            model.addAttribute("patientInfoId", patientId );
            model.addAttribute("report", riskReport);
        } catch (Exception e) {
            log.warn("Error while creating report for patient id {} , exeception {}", patientId, e);
            String riskReportNotEnoghElements = "Patient info or history not found, impossible to create report";
            model.addAttribute("notEnoughElements", riskReportNotEnoghElements);
        }

        return "riskReport";
    }
}
