package com.solncev.service;

import com.solncev.dto.NoteForm;
import com.solncev.model.Note;
import com.solncev.model.User;
import com.solncev.repository.NoteRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Transactional(readOnly = true)
    public List<Note> getMyNotes(User author) {
        return noteRepository.findByAuthor(author);
    }

    @Transactional(readOnly = true)
    public List<Note> getPublicNotes() {
        return noteRepository.findByIsPublicTrue();
    }

    @Transactional
    public Note create(User author, NoteForm form) {
        Note note = new Note();
        note.setAuthor(author);
        note.setTitle(form.getTitle());
        note.setContent(form.getContent());
        note.setPublic(form.isPublic());
        return noteRepository.save(note);
    }

    @Transactional(readOnly = true)
    public Note getOwnedOrThrow(User currentUser, Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Note not found: " + noteId));

        if (!note.getAuthor().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You cannot access чужую заметку");
        }
        return note;
    }

    @Transactional
    public Note update(User currentUser, Long noteId, NoteForm form) {
        Note note = getOwnedOrThrow(currentUser, noteId);
        note.setTitle(form.getTitle());
        note.setContent(form.getContent());
        note.setPublic(form.isPublic());
        return noteRepository.save(note);
    }
    @Transactional
    public void delete(User currentUser, Long noteId) {
        Note note = getOwnedOrThrow(currentUser, noteId);
        noteRepository.delete(note);
    }

    public List<Note> adminGetAll() {
        return noteRepository.findAll();
    }

    public void adminDelete(Long noteId) {
        noteRepository.deleteById(noteId);
    }
}