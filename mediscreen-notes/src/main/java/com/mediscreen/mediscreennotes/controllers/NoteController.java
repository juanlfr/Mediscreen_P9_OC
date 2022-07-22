package com.mediscreen.mediscreennotes.controllers;

import com.mediscreen.mediscreennotes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;
}
