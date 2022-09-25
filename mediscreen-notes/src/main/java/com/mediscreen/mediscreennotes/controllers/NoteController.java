package com.mediscreen.mediscreennotes.controllers;

import com.mediscreen.mediscreennotes.models.Note;
import com.mediscreen.mediscreennotes.services.NoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/patHistory")
public class NoteController {
    @Autowired
    private NoteService noteService;

    private final Logger log = LogManager.getLogger(NoteController.class);


    @GetMapping("/{id}")
    public ResponseEntity<Optional<Note>> getNoteFromPatient(@PathVariable("id") @NotBlank String id) {
        Optional<Note> note = noteService.findById(id);
        if (note.isPresent()) {
            return new ResponseEntity<>(note, HttpStatus.OK);
        } else {
            throw new NoSuchElementException();
        }
    }
    @GetMapping("/allNotes/{id}")
    public List<Note> getPatientHistoryNotes(@PathVariable("id") @NotBlank String id) {
        try {
            log.info("Finding all notes by patient");
            return noteService.findAllById(id);
        } catch (Exception e) {
            log.error("Error while fetching all note from patient with id: " + id);
        }
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> createNote(@RequestBody Note note) {
        try {
            log.info("Creating patient history note" + note.toString());
            note.setCreationDate(LocalDateTime.now());
            Note noteAdded = noteService.saveNote(note);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(noteAdded.getPatientId())
                    .toUri();
            return ResponseEntity.created(location).build();

        } catch (Exception e) {
            log.error("Error on patient creation");
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
