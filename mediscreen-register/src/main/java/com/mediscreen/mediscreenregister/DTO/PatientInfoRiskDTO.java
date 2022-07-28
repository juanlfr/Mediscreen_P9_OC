package com.mediscreen.mediscreenregister.DTO;

import java.time.LocalDate;

public class PatientInfoRiskDTO {

    private LocalDate dateOfBirth;
    private String gender;

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
