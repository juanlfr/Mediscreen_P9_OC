package com.mediscreen.mediscreenregister.proxies;

import com.mediscreen.mediscreenregister.models.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "mediscreen-notes", url = "${register.feignNotesMicroserviceURL}:8081")
public interface MediscreenNotesProxy {
    @GetMapping("/patHistory/{id}")
    ResponseEntity<Optional<NoteBean>> getNotesFromPatient(@PathVariable("id") String id);
    @PostMapping("/patHistory/add")
    ResponseEntity<Void> createNote(@RequestBody NoteBean note);
    @GetMapping("/patHistory/allNotes/{id}")
    List<NoteBean> findAllById(@PathVariable("id") String id);
}
