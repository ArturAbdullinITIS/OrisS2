package com.solncev.controller;

import com.solncev.dto.AdminNoteDto;
import com.solncev.model.Note;
import com.solncev.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/notes")
public class AdminNoteController {

    private final NoteService noteService;

    public AdminNoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<AdminNoteDto> all() {
        return noteService.adminGetAll().stream()
                .map(this::toDto)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        noteService.adminDelete(id);
        return ResponseEntity.noContent().build();
    }

    private AdminNoteDto toDto(Note note) {
        AdminNoteDto dto = new AdminNoteDto();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setPublic(note.isPublic());

        dto.setAuthorId(note.getAuthor().getId());
        dto.setAuthorUsername(note.getAuthor().getUsername());
        return dto;
    }
}