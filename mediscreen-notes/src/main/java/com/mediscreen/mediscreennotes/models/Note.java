package com.mediscreen.mediscreennotes.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "notes")
public class Note {

    @Id
    private String id;
    private String note;
    private LocalDateTime creationDate;

    public Note() {
    }

    public Note(String id, String note, LocalDateTime creationDate) {
        this.id = id;
        this.note = note;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return getId().equals(note1.getId()) && Objects.equals(getNote(), note1.getNote()) && Objects.equals(getCreationDate(), note1.getCreationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNote(), getCreationDate());
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", note='" + note + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
