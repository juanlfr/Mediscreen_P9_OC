package com.mediscreen.mediscreennotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediscreen.mediscreennotes.controllers.NoteController;
import com.mediscreen.mediscreennotes.models.Note;
import com.mediscreen.mediscreennotes.services.NoteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NoteController.class)
public class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    private Note note;

    @BeforeEach
    private void setUpNote() {
        note = new Note();
        note.setPatientId("1");
        note.setNote("Doctor Comment");
        note.setCreationDate(LocalDateTime.now());

    }

    @Test
    public void getNoteTest() throws Exception {
        Mockito.when(noteService.findById(Mockito.any(String.class))).thenReturn(Optional.ofNullable(note));
        this.mockMvc.perform(get("/patHistory/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note", is("Doctor Comment")));

    }
    @Test
    public void getAllNotesTest() throws Exception {
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        Mockito.when(noteService.findAllById(Mockito.any(String.class))).thenReturn(notes);
        this.mockMvc.perform(get("/patHistory/allNotes/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));

    }
    @Test
    public void createNoteTest() throws Exception {

        when(noteService.saveNote(Mockito.any(Note.class))).thenReturn(note);

        this.mockMvc.perform(post("/patHistory/add")
                        .content(toJsonString(note))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    private String toJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule((new JavaTimeModule()));
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


