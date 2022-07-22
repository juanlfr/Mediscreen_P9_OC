package com.mediscreen.mediscreennotes.repositories;

import com.mediscreen.mediscreennotes.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
}
