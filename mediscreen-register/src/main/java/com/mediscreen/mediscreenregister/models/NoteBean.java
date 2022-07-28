package com.mediscreen.mediscreenregister.models;


import java.time.LocalDateTime;

public class NoteBean {

    private String id;
    private String patientId;
    private String note;
    private LocalDateTime creationDate;

    public NoteBean() {
    }
    public NoteBean(String id, String patientId, String note, LocalDateTime creationDate) {
        this.id = id;
        this.patientId = patientId;
        this.note = note;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
