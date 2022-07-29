package com.mediscreen.mediscreenrisk.model;

public class PatientHistoryBean {

    private String note;

    public PatientHistoryBean() {
    }

    public PatientHistoryBean(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "PatientHistoryBean{" +
                "note='" + note + '\'' +
                '}';
    }
}
