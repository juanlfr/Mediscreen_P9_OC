package com.mediscreen.mediscreennotes.services;

import com.mediscreen.mediscreennotes.models.Note;
import com.mediscreen.mediscreennotes.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Optional<Note> findById(String id) {
        return noteRepository.findById(id);
    }

    @Override
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public List<Note> findAllById(String id) {
        return noteRepository.findAllNotesByPatientId(id);
    }
}
