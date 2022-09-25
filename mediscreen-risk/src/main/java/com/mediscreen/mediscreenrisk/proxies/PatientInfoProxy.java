package com.mediscreen.mediscreenrisk.proxies;

import com.mediscreen.mediscreenrisk.model.PatientInfoBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mediscreen-register", url = "${risk.feignRegisterMicroserviceURL}:8080")
public interface PatientInfoProxy {
    @GetMapping("/api/v1/patient/riskInfo/{id}")
    PatientInfoBean getPatientInfo(@PathVariable("id") Long id);
}
