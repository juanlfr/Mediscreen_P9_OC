package com.mediscreen.mediscreenregister.models;


import java.time.LocalDateTime;

public class NoteBean {

    private String patientId;
    private String note;
    private LocalDateTime creationDate;

    public NoteBean() {
    }
    public NoteBean(String patientId, String note, LocalDateTime creationDate) {
        this.patientId = patientId;
        this.note = note;
        this.creationDate = creationDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
