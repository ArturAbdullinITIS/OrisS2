package com.solncev.controller;

import com.solncev.dto.NoteForm;
import com.solncev.model.Note;
import com.solncev.model.User;
import com.solncev.service.CurrentUserService;
import com.solncev.service.CustomUserDetails;
import com.solncev.service.NoteService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final CurrentUserService currentUserService;

    public NoteController(NoteService noteService, CurrentUserService currentUserService) {
        this.noteService = noteService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public String myNotes(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        User me = currentUserService.getByUsernameOrThrow(principal.getUsername());
        List<Note> notes = noteService.getMyNotes(me);
        model.addAttribute("notes", notes);
        return "notes";
    }

    @GetMapping("/public")
    public String publicNotes(Model model) {
        List<Note> notes = noteService.getPublicNotes();
        model.addAttribute("notes", notes);
        return "public_notes";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("form", new NoteForm());
        model.addAttribute("mode", "create");
        return "note_form";
    }

    @PostMapping("/create")
    public String create(@AuthenticationPrincipal CustomUserDetails principal,
                         @ModelAttribute("form") NoteForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "create");
            return "note_form";
        }
        User me = currentUserService.getByUsernameOrThrow(principal.getUsername());
        noteService.create(me, form);
        return "redirect:/notes";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@AuthenticationPrincipal CustomUserDetails principal,
                           @PathVariable("id") Long id,
                           Model model) {
        User me = currentUserService.getByUsernameOrThrow(principal.getUsername());
        Note note = noteService.getOwnedOrThrow(me, id);

        NoteForm form = new NoteForm();
        form.setTitle(note.getTitle());
        form.setContent(note.getContent());
        form.setPublic(note.isPublic());

        model.addAttribute("form", form);
        model.addAttribute("noteId", id);
        model.addAttribute("mode", "edit");
        return "note_form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@AuthenticationPrincipal CustomUserDetails principal,
                       @PathVariable("id") Long id,
                       @ModelAttribute("form") NoteForm form,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("noteId", id);
            model.addAttribute("mode", "edit");
            return "note_form";
        }
        User me = currentUserService.getByUsernameOrThrow(principal.getUsername());
        noteService.update(me, id, form);
        return "redirect:/notes";
    }

    @PostMapping("/{id}/delete")
    public String delete(@AuthenticationPrincipal CustomUserDetails principal,
                         @PathVariable("id") Long id) {
        User me = currentUserService.getByUsernameOrThrow(principal.getUsername());
        noteService.delete(me, id);
        return "redirect:/notes";
    }
}