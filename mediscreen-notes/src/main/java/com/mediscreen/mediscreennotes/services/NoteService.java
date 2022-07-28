package com.mediscreen.mediscreennotes.services;

import com.mediscreen.mediscreennotes.models.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    Optional<Note> findById(String id);

    Note saveNote(Note note);

    List<Note> findAllById(String id);
}
