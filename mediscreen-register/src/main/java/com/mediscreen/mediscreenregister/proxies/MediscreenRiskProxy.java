package com.mediscreen.mediscreenregister.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mediscreen-risk", url = "localhost:8082")
public interface MediscreenRiskProxy {

    @PostMapping("/assess/id")
    ResponseEntity<String> generateReport(@RequestBody String patientId);


}
