package com.mediscreen.mediscreennotes.repositories;

import com.mediscreen.mediscreennotes.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findAllNotesByPatientId(String id);
}
