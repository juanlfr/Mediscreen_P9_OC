package com.mediscreen.mediscreenrisk.model;

import java.time.LocalDate;

public class PatientInfoBean {
    private LocalDate dateOfBirth;
    private String gender;

    public PatientInfoBean() {
    }

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

    @Override
    public String toString() {
        return "PatientInfoBean{" +
                "dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                '}';
    }
}
