package com.mediscreen.mediscreenregister.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "register", ignoreUnknownFields = false)
public class RegisterProperties {
     private String feignNotesMicroserviceURL = "localhost";
    private String feignRiskMicroserviceURL = "localhost";


    public String getFeignNotesMicroserviceURL() {
        return feignNotesMicroserviceURL;
    }

    public void setFeignNotesMicroserviceURL(String feignNotesMicroserviceURL) {
        this.feignNotesMicroserviceURL = feignNotesMicroserviceURL;
    }

    public String getFeignRiskMicroserviceURL() {
        return feignRiskMicroserviceURL;
    }

    public void setFeignRiskMicroserviceURL(String feignRiskMicroserviceURL) {
        this.feignRiskMicroserviceURL = feignRiskMicroserviceURL;
    }
}
