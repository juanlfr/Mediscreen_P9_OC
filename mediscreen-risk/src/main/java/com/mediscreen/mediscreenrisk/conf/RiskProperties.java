package com.mediscreen.mediscreenrisk.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "risk", ignoreUnknownFields = false)
public class RiskProperties {
    private String feignNotesMicroserviceURL = "localhost";
    private String feignRegisterMicroserviceURL = "localhost";

    public String getFeignNotesMicroserviceURL() {
        return feignNotesMicroserviceURL;
    }

    public void setFeignNotesMicroserviceURL(String feignNotesMicroserviceURL) {
        this.feignNotesMicroserviceURL = feignNotesMicroserviceURL;
    }

    public String getFeignRegisterMicroserviceURL() {
        return feignRegisterMicroserviceURL;
    }

    public void setFeignRegisterMicroserviceURL(String feignRegisterMicroserviceURL) {
        this.feignRegisterMicroserviceURL = feignRegisterMicroserviceURL;
    }
}
