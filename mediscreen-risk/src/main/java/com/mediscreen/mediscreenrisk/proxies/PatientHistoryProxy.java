package com.mediscreen.mediscreenrisk.proxies;

import com.mediscreen.mediscreenrisk.model.PatientHistoryBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "mediscreen-notes", url = "${risk.feignNotesMicroserviceURL}:8081")
public interface PatientHistoryProxy {
    @GetMapping("/patHistory/allNotes/{id}")
    List<PatientHistoryBean> getPatientHistoryNotes(@PathVariable("id") String id);

}
